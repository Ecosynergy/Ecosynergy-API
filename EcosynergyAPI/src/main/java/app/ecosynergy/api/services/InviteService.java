package app.ecosynergy.api.services;

import app.ecosynergy.api.controllers.InviteController;
import app.ecosynergy.api.data.vo.v1.InviteVO;
import app.ecosynergy.api.exceptions.*;
import app.ecosynergy.api.mapper.DozerMapper;
import app.ecosynergy.api.models.*;
import app.ecosynergy.api.repositories.InviteRepository;
import app.ecosynergy.api.repositories.TeamMemberRepository;
import app.ecosynergy.api.repositories.TeamRepository;
import app.ecosynergy.api.repositories.UserRepository;
import app.ecosynergy.api.services.notification.InvitationNotificationService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class InviteService {
    private static final Logger logger = Logger.getLogger(InviteService.class.getName());
    @Autowired
    private InviteRepository inviteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TeamMemberRepository teamMemberRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamService teamService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private InvitationNotificationService invitationNotificationService;
    @Autowired
    private PagedResourcesAssembler<InviteVO> assembler;
    private User currentUser;

    private User getCurrentUser() {
        return userService.getCurrentUser();
    }

    public PagedModel<EntityModel<InviteVO>> findAllPaged(int page, int limit, String direction) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "createdAt"));

        Page<Invite> invitePage = inviteRepository.findAll(pageable);
        Page<InviteVO> voPage = invitePage.map(i -> DozerMapper.parseObject(i, InviteVO.class));

        voPage.map(inviteVO -> {
            Team team = teamRepository.findById(inviteVO.getTeamId()).orElseThrow();

            inviteVO.setCreatedAt(inviteVO.getCreatedAt().withZoneSameInstant(team.getTimeZone()));
            inviteVO.setUpdatedAt(inviteVO.getUpdatedAt().withZoneSameInstant(team.getTimeZone()));

            inviteVO.add(linkTo(methodOn(InviteController.class).findById(inviteVO.getId())).withSelfRel());
            return inviteVO;
        });

        Link link = linkTo(methodOn(InviteController.class)
                .findAll(pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSort().toString()
                ))
                .withSelfRel();

        return assembler.toModel(voPage, link);
    }

    public InviteVO findById(Long id) {
        Invite invite = inviteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invite not found for this id: " + id));

        InviteVO inviteVO = DozerMapper.parseObject(invite, InviteVO.class);
        inviteVO.setCreatedAt(inviteVO.getCreatedAt().withZoneSameInstant(invite.getTeam().getTimeZone()));
        inviteVO.setUpdatedAt(inviteVO.getUpdatedAt().withZoneSameInstant(invite.getTeam().getTimeZone()));
        inviteVO.add(linkTo(methodOn(InviteController.class).findById(inviteVO.getId())).withSelfRel());
        return inviteVO;
    }

    @Transactional
    public InviteVO createInvite(InviteVO inviteVO) throws MessagingException {
        if (inviteVO.getRecipientId() == null || inviteVO.getTeamId() == null)
            throw new RequiredObjectIsNullException("Invalid User or Team");

        currentUser = getCurrentUser();

        Optional<User> recipient = userRepository.findById(inviteVO.getRecipientId());
        Optional<Team> team = teamRepository.findById(inviteVO.getTeamId());

        if (recipient.isEmpty() || team.isEmpty()) {
            throw new ResourceNotFoundException("Invalid User or Team");
        }

        boolean isAdmin = teamMemberRepository.existsByTeamIdAndUserIdAndRole(team.get().getId(), currentUser.getId(), Role.ADMINISTRATOR);
        boolean isFounder = teamMemberRepository.existsByTeamIdAndUserIdAndRole(team.get().getId(), currentUser.getId(), Role.FOUNDER);
        if (!isAdmin && !isFounder) throw new UnauthorizedException("User is not an ADMINISTRATOR of the team");

        boolean isMember = teamMemberRepository.existsByTeamIdAndUserId(team.get().getId(), recipient.get().getId());
        if (isMember) throw new ResourceAlreadyExistsException("Recipient is already a member of the team");

        boolean inviteExists = inviteRepository.existsBySenderIdAndRecipientIdAndTeamIdAndStatus(
                currentUser.getId(),
                recipient.get().getId(),
                team.get().getId(),
                InviteStatus.PENDING
        );
        if (inviteExists)
            throw new ResourceAlreadyExistsException("An invite with the same sender, recipient, and team already exists");

        Invite invite = new Invite();
        invite.setSender(currentUser);
        invite.setRecipient(recipient.get());
        invite.setTeam(team.get());
        invite.setStatus(InviteStatus.PENDING);

        Invite savedInvite = inviteRepository.save(invite);

        emailService.sendInviteEmail(recipient.get().getEmail(), team.get().getName(), currentUser.getFullName());

        invitationNotificationService.sendInviteNotification(savedInvite.getRecipient().getTokens(), savedInvite);

        InviteVO savedInviteVO = DozerMapper.parseObject(savedInvite, InviteVO.class);
        savedInviteVO.setCreatedAt(savedInviteVO.getCreatedAt().withZoneSameInstant(savedInvite.getTeam().getTimeZone()));
        savedInviteVO.setUpdatedAt(savedInviteVO.getUpdatedAt().withZoneSameInstant(savedInvite.getTeam().getTimeZone()));
        savedInviteVO.add(linkTo(methodOn(InviteController.class).findById(savedInviteVO.getId())).withSelfRel());

        return savedInviteVO;
    }

    @Transactional
    public InviteVO acceptInvite(Long inviteId) throws MessagingException {
        Invite invite = inviteRepository.findById(inviteId)
                .orElseThrow(() -> new ResourceNotFoundException("Invite Not Found"));

        if (invite.getStatus() != InviteStatus.PENDING) {
            throw new InviteAlreadyRespondedException("Invite has already been responded to");
        }

        currentUser = getCurrentUser();

        if (!Objects.equals(invite.getRecipient().getId(), currentUser.getId()))
            throw new UnauthorizedActionException("You are not authorized to accept this invite");

        invite.setStatus(InviteStatus.ACCEPTED);

        TeamMemberId teamMemberId = new TeamMemberId(
                invite.getTeam().getId(),
                invite.getRecipient().getId()
        );

        teamService.addMember(teamMemberId, Role.COMMON_USER);

        Invite updatedInvite = inviteRepository.save(invite);

        emailService.sendInviteAcceptedNotification(updatedInvite.getSender().getEmail(), updatedInvite.getSender().getFullName().split(" ")[0], updatedInvite.getRecipient().getFullName(), updatedInvite.getTeam().getName());

        invitationNotificationService.sendInviteAcceptedNotification(updatedInvite.getSender().getTokens(), updatedInvite);

        InviteVO inviteVO = DozerMapper.parseObject(updatedInvite, InviteVO.class);
        inviteVO.setCreatedAt(inviteVO.getCreatedAt().withZoneSameInstant(invite.getTeam().getTimeZone()));
        inviteVO.setUpdatedAt(inviteVO.getUpdatedAt().withZoneSameInstant(invite.getTeam().getTimeZone()));
        inviteVO.add(linkTo(methodOn(InviteController.class).findById(inviteVO.getId())).withSelfRel());
        return inviteVO;
    }

    @Transactional
    public InviteVO declineInvite(Long inviteId) throws MessagingException {
        Invite invite = inviteRepository.findById(inviteId)
                .orElseThrow(() -> new IllegalArgumentException("Invite Not Found"));

        if (invite.getStatus() != InviteStatus.PENDING) {
            throw new InviteAlreadyRespondedException("Invite has already been responded to");
        }

        currentUser = getCurrentUser();

        if (!Objects.equals(invite.getRecipient().getId(), currentUser.getId()))
            throw new UnauthorizedActionException("You are not authorized to decline this invite");

        invite.setStatus(InviteStatus.DECLINED);
        Invite updatedInvite = inviteRepository.save(invite);

        emailService.sendInviteRejectedNotification(updatedInvite.getSender().getEmail(), updatedInvite.getSender().getFullName().split(" ")[0], updatedInvite.getRecipient().getFullName(), updatedInvite.getTeam().getName());

        invitationNotificationService.sendInviteDeclinedNotification(updatedInvite.getSender().getTokens(), updatedInvite);

        InviteVO inviteVO = DozerMapper.parseObject(updatedInvite, InviteVO.class);
        inviteVO.setCreatedAt(inviteVO.getCreatedAt().withZoneSameInstant(invite.getTeam().getTimeZone()));
        inviteVO.setUpdatedAt(inviteVO.getUpdatedAt().withZoneSameInstant(invite.getTeam().getTimeZone()));
        inviteVO.add(linkTo(methodOn(InviteController.class).findById(inviteVO.getId())).withSelfRel());

        return inviteVO;
    }

    public List<InviteVO> findPendingInvitesByRecipient(Long recipientId) {
        return inviteRepository.findByRecipientIdAndStatus(recipientId, InviteStatus.PENDING)
                .stream()
                .map(invite -> {
                    InviteVO inviteVO = DozerMapper.parseObject(invite, InviteVO.class);

                    inviteVO.setCreatedAt(inviteVO.getCreatedAt().withZoneSameInstant(invite.getTeam().getTimeZone()));
                    inviteVO.setUpdatedAt(inviteVO.getUpdatedAt().withZoneSameInstant(invite.getTeam().getTimeZone()));

                    inviteVO.add(linkTo(methodOn(InviteController.class).findById(inviteVO.getId())).withSelfRel());
                    return inviteVO;
                })
                .collect(Collectors.toList());
    }

    public List<InviteVO> findInvitesBySender(Long senderId) {
        return inviteRepository.findBySenderId(senderId)
                .stream()
                .map(invite -> {
                    InviteVO inviteVO = DozerMapper.parseObject(invite, InviteVO.class);

                    inviteVO.setCreatedAt(inviteVO.getCreatedAt().withZoneSameInstant(invite.getTeam().getTimeZone()));
                    inviteVO.setUpdatedAt(inviteVO.getUpdatedAt().withZoneSameInstant(invite.getTeam().getTimeZone()));

                    inviteVO.add(linkTo(methodOn(InviteController.class).findById(inviteVO.getId())).withSelfRel());
                    return inviteVO;
                })
                .collect(Collectors.toList());
    }

    public List<InviteVO> findInvitesByRecipient(Long recipientId) {
        return inviteRepository.findByRecipientId(recipientId)
                .stream()
                .map(invite -> {
                    InviteVO inviteVO = DozerMapper.parseObject(invite, InviteVO.class);

                    inviteVO.setCreatedAt(inviteVO.getCreatedAt().withZoneSameInstant(invite.getTeam().getTimeZone()));
                    inviteVO.setUpdatedAt(inviteVO.getUpdatedAt().withZoneSameInstant(invite.getTeam().getTimeZone()));

                    inviteVO.add(linkTo(methodOn(InviteController.class).findById(inviteVO.getId())).withSelfRel());
                    return inviteVO;
                })
                .collect(Collectors.toList());
    }

    public List<InviteVO> findByTeamId(Long teamId) {
        return inviteRepository.findByTeamId(teamId)
                .stream()
                .map(invite -> {
                    InviteVO inviteVO = DozerMapper.parseObject(invite, InviteVO.class);

                    inviteVO.setCreatedAt(inviteVO.getCreatedAt().withZoneSameInstant(invite.getTeam().getTimeZone()));
                    inviteVO.setUpdatedAt(inviteVO.getUpdatedAt().withZoneSameInstant(invite.getTeam().getTimeZone()));

                    inviteVO.add(linkTo(methodOn(InviteController.class).findById(inviteVO.getId())).withSelfRel());
                    return inviteVO;
                })
                .collect(Collectors.toList());
    }
}
