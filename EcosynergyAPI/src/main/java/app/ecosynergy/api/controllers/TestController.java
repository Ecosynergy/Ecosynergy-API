package app.ecosynergy.api.controllers;

import app.ecosynergy.api.data.vo.v1.UserVO;
import app.ecosynergy.api.exceptions.ResourceNotFoundException;
import app.ecosynergy.api.models.User;
import app.ecosynergy.api.models.UserToken;
import app.ecosynergy.api.repositories.UserRepository;
import app.ecosynergy.api.services.UserService;
import app.ecosynergy.api.services.notification.NotificationService;
import app.ecosynergy.api.util.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(
            value = "/api/notification/v1/test/{username}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public ResponseEntity<String> sendNotification(@PathVariable("username") String username) {
        System.out.println(username);

        UserVO userVO = userService.findByUsername(username);

        User user = userRepository.findById(userVO.getKey()).orElse(null);

        if(user == null) throw new ResourceNotFoundException("User not found");

        for (UserToken userToken : user.getTokens()) {
            notificationService.sendNotificationToUser(userToken.getToken(), "Recado do Andinho", "Oi Rafael Guerra");
        }

        return ResponseEntity.ok("Message Sent");
    }
}
