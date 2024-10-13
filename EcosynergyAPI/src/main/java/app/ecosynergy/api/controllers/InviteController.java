package app.ecosynergy.api.controllers;

import app.ecosynergy.api.data.vo.v1.InviteVO;
import app.ecosynergy.api.services.InviteService;
import app.ecosynergy.api.util.MediaType;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invite/v1")
public class InviteController {

    @Autowired
    private InviteService inviteService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    public ResponseEntity<PagedModel<EntityModel<InviteVO>>> findAll(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "5") Integer limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        page--;

        PagedModel<EntityModel<InviteVO>> invites = inviteService.findAllPaged(page, limit, direction);

        return ResponseEntity.ok(invites);
    }

    @GetMapping(value = "/id/{id}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    public ResponseEntity<InviteVO> findById(@PathVariable Long id) {
        InviteVO invite = inviteService.findById(id);
        return ResponseEntity.ok(invite);
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public ResponseEntity<InviteVO> create(
            @RequestBody InviteVO invite
    ) throws MessagingException {
        InviteVO inviteVO = inviteService.createInvite(invite);
        return ResponseEntity.ok(inviteVO);
    }

    @PutMapping(
            value = "/accept/{inviteId}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public ResponseEntity<InviteVO> acceptInvite(@PathVariable Long inviteId) throws MessagingException {
        InviteVO inviteVO = inviteService.acceptInvite(inviteId);
        return ResponseEntity.ok(inviteVO);
    }

    @PutMapping(
            value = "/decline/{inviteId}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public ResponseEntity<InviteVO> declineInvite(@PathVariable Long inviteId) throws MessagingException {
        InviteVO inviteVO = inviteService.declineInvite(inviteId);
        return ResponseEntity.ok(inviteVO);
    }

    @GetMapping(
            value = "/pending/recipient/{recipientId}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public ResponseEntity<List<InviteVO>> findPendingInvitesByRecipient(@PathVariable Long recipientId) {
        List<InviteVO> invites = inviteService.findPendingInvitesByRecipient(recipientId);
        return ResponseEntity.ok(invites);
    }

    @GetMapping(
            value = "/sent/sender/{senderId}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public ResponseEntity<List<InviteVO>> findInvitesBySender(@PathVariable Long senderId) {
        List<InviteVO> invites = inviteService.findInvitesBySender(senderId);
        return ResponseEntity.ok(invites);
    }

    @GetMapping(
            value = "/received/recipient/{recipientId}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public ResponseEntity<List<InviteVO>> findInvitesByRecipient(@PathVariable Long recipientId) {
        List<InviteVO> invites = inviteService.findInvitesByRecipient(recipientId);
        return ResponseEntity.ok(invites);
    }
}