package app.ecosynergy.api.services.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvitationNotificationService {

    @Autowired
    private NotificationService notificationService;

    public void sendInviteNotification(String inviteeToken, String senderName, String teamName) {
        String title = "New team invitation!";
        String body = senderName + " invited you to join the team " + teamName + " .";
        notificationService.sendNotificationToUser(inviteeToken, title, body);
    }

    public void sendInviteAcceptedNotification(String senderToken, String inviteeName, String teamName) {
        String title = "Invitation Accepted";
        String body = inviteeName + " accepted your invitation to join the team " + teamName + " !";
        notificationService.sendNotificationToUser(senderToken, title, body);
    }

    public void sendInviteDeclinedNotification(String senderToken, String inviteeName, String teamName) {
        String title = "Invitation Declined";
        String body = inviteeName + " declined your invitation to join the team " + teamName + " .";
        notificationService.sendNotificationToUser(senderToken, title, body);
    }
}
