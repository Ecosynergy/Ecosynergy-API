package app.ecosynergy.api.services;

import app.ecosynergy.api.data.vo.v1.InviteVO;
import app.ecosynergy.api.exceptions.*;
import app.ecosynergy.api.mapper.DozerMapper;
import app.ecosynergy.api.models.*;
import app.ecosynergy.api.repositories.InviteRepository;
import app.ecosynergy.api.repositories.TeamMemberRepository;
import app.ecosynergy.api.repositories.TeamRepository;
import app.ecosynergy.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class InviteService {
    @Autowired
    private InviteRepository inviteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private PagedResourcesAssembler<InviteVO> assembler;

    private static final Logger logger = Logger.getLogger(InviteService.class.getName());

    public PagedModel<EntityModel<InviteVO>> findAllPaged(int page, int limit, String direction) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "createdAt"));

        Page<Invite> invitePage = inviteRepository.findAll(pageable);

        List<InviteVO> inviteVOs = invitePage.stream()
                .map(invite -> DozerMapper.parseObject(invite, InviteVO.class))
                .toList();

        return assembler.toModel(invitePage.map(invite -> EntityModel.of(DozerMapper.parseObject(invite, InviteVO.class)).getContent()));
    }

    public InviteVO findById(Long id) {
        Invite invite = inviteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invite not found for this id: " + id));

        return DozerMapper.parseObject(invite, InviteVO.class);
    }

    @Transactional
    public InviteVO createInvite(InviteVO inviteVO) {
        if(inviteVO.getSenderId() == null || inviteVO.getRecipientId() == null || inviteVO.getTeamId() == null) throw new RequiredObjectIsNullException("Invalid User ou Team");

        Optional<User> sender = userRepository.findById(inviteVO.getSenderId());
        Optional<User> recipient = userRepository.findById(inviteVO.getRecipientId());
        Optional<Team> team = teamRepository.findById(inviteVO.getTeamId());

        if (sender.isEmpty() || recipient.isEmpty() || team.isEmpty()) {
            throw new ResourceNotFoundException("Invalid User ou Team");
        }

        boolean isAdmin = teamMemberRepository.existsByTeamIdAndUserIdAndRole(team.get().getId(), sender.get().getId(), Role.ADMINISTRATOR);
        if(!isAdmin) throw new UnauthorizedException("User is not an ADMINISTRATOR of the team");

        boolean isMember = teamMemberRepository.existsByTeamIdAndUserId(team.get().getId(), recipient.get().getId());
        if(isMember) throw new ResourceAlreadyExistsException("Recipient is already a member of the team");

        boolean inviteExists = inviteRepository.existsBySenderIdAndRecipientIdAndTeamIdAndStatus(
                sender.get().getId(),
                recipient.get().getId(),
                team.get().getId(),
                InviteStatus.PENDING
        );
        if(inviteExists) throw new ResourceAlreadyExistsException("An invite with the same sender, recipient, and team already exists");

        Invite invite = new Invite();
        invite.setSender(sender.get());
        invite.setRecipient(recipient.get());
        invite.setTeam(team.get());
        invite.setStatus(InviteStatus.PENDING);

        Invite savedInvite = inviteRepository.save(invite);

        return DozerMapper.parseObject(savedInvite, InviteVO.class);
    }

    @Transactional
    public InviteVO acceptInvite(Long inviteId) {
        Invite invite = inviteRepository.findById(inviteId)
                .orElseThrow(() -> new ResourceNotFoundException("Invite Not Found"));

        if (invite.getStatus() != InviteStatus.PENDING) {
            throw new InviteAlreadyRespondedException("Invite has already been responded to");
        }

        invite.setStatus(InviteStatus.ACCEPTED);

        TeamMemberId teamMemberId = new TeamMemberId(
                invite.getTeam().getId(),
                invite.getRecipient().getId()
        );

        teamService.addMember(teamMemberId, Role.COMMON_USER);

        Invite updatedInvite = inviteRepository.save(invite);
        return DozerMapper.parseObject(updatedInvite, InviteVO.class);
    }

    @Transactional
    public InviteVO declineInvite(Long inviteId) {
        Invite invite = inviteRepository.findById(inviteId)
                .orElseThrow(() -> new IllegalArgumentException("Invite Not Found"));

        if (invite.getStatus() != InviteStatus.PENDING) {
            throw new InviteAlreadyRespondedException("Invite has already been responded to");
        }

        invite.setStatus(InviteStatus.DECLINED);
        Invite updatedInvite = inviteRepository.save(invite);
        return DozerMapper.parseObject(updatedInvite, InviteVO.class);
    }

    public List<InviteVO> findPendingInvitesByRecipient(Long recipientId) {
        return inviteRepository.findByRecipientIdAndStatus(recipientId, InviteStatus.PENDING)
                .stream()
                .map(invite -> DozerMapper.parseObject(invite, InviteVO.class))
                .collect(Collectors.toList());
    }

    public List<InviteVO> findInvitesBySender(Long senderId) {
        return inviteRepository.findBySenderId(senderId)
                .stream()
                .map(invite -> DozerMapper.parseObject(invite, InviteVO.class))
                .collect(Collectors.toList());
    }

    public List<InviteVO> findInvitesByRecipient(Long recipientId) {
        return inviteRepository.findByRecipientId(recipientId)
                .stream()
                .map(invite -> DozerMapper.parseObject(invite, InviteVO.class))
                .collect(Collectors.toList());
    }
}
