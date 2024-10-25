package app.ecosynergy.api.services.notification;

import app.ecosynergy.api.models.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvitationNotificationService {

    @Autowired
    private NotificationService notificationService;

    public void sendInviteNotification(List<UserToken> recipientTokens, String senderName, String teamName) {
        String title = "New team invitation!";
        String body = senderName + " invited you to join the team " + teamName + " .";
        
        for (UserToken recipientToken : recipientTokens) {
            notificationService.sendNotificationToUser(recipientToken.getToken(), title, body);
        }
        
    }

    public void sendInviteAcceptedNotification(List<UserToken> senderTokens, String recipientName, String teamName) {
        String title = "Invitation Accepted";
        String body = recipientName + " accepted your invitation to join the team " + teamName + " !";
        
        for (UserToken senderToken : senderTokens) {
            notificationService.sendNotificationToUser(senderToken.getToken(), title, body);
        }
    }

    public void sendInviteDeclinedNotification(List<UserToken> senderTokens, String recipientName, String teamName) {
        String title = "Invitation Declined";
        String body = recipientName + " declined your invitation to join the team " + teamName + " .";

        for (UserToken senderToken : senderTokens) {
            notificationService.sendNotificationToUser(senderToken.getToken(), title, body);
        }
    }
}
