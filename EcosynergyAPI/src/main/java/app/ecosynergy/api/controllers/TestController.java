package app.ecosynergy.api.controllers;

import app.ecosynergy.api.data.vo.v1.UserVO;
import app.ecosynergy.api.exceptions.ResourceNotFoundException;
import app.ecosynergy.api.models.*;
import app.ecosynergy.api.repositories.TeamRepository;
import app.ecosynergy.api.repositories.UserRepository;
import app.ecosynergy.api.services.EmailService;
import app.ecosynergy.api.services.UserService;
import app.ecosynergy.api.services.notification.NotificationService;
import app.ecosynergy.api.util.MediaType;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @PostMapping(
            value = "/api/notification/v1/test/{username}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public ResponseEntity<String> sendNotification(@PathVariable("username") String username) {

        UserVO userVO = userService.findByUsername(username);

        User user = userRepository.findById(userVO.getKey()).orElse(null);

        if (user == null) throw new ResourceNotFoundException("User not found");

        Map<String, String> params = new HashMap<>();
        params.put("title", "Recado do Andinho");
        params.put("body", "Pega a visão");
        params.put("type", "test");
        params.put("vision", "get the vision");

        for (UserToken userToken : user.getTokens()) {
            notificationService.sendNotificationToUser(userToken.getToken(), params);
        }

        return ResponseEntity.ok("Message Sent");
    }

    @PostMapping(
            value = "/api/email/v1/test/sentInvite/",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public ResponseEntity<String> sendInviteEmail() throws MessagingException {
        User user = userService.getCurrentUser();

        Team team = teamRepository.findByHandle("ecosynergyofc").orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        User rafinhaGuerra = userRepository.findByEmail("guerrafaell@gmail.com");

        if (user == null) throw new ResourceNotFoundException("User not found");

        Invite invite = new Invite();
        invite.setSender(rafinhaGuerra);
        invite.setRecipient(user);
        invite.setTeam(team);
        invite.setStatus(InviteStatus.PENDING);

        emailService.sendInviteEmail(invite);

        return ResponseEntity.ok("Mail Sent");
    }
}
