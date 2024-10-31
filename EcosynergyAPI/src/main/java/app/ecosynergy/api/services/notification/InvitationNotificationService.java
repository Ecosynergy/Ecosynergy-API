package app.ecosynergy.api.services.notification;

import app.ecosynergy.api.models.Invite;
import app.ecosynergy.api.models.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvitationNotificationService {

    @Autowired
    private NotificationService notificationService;

    public void sendInviteNotification(List<UserToken> recipientTokens, Invite invite) {
        String title = "New team invitation!";
        String body = invite.getSender().getUserName() + " invited you to join the team " + invite.getTeam().getName() + " .";

        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("body", body);
        params.put("inviteId", invite.getId().toString());

        for (UserToken recipientToken : recipientTokens) {
            notificationService.sendNotificationToUser(recipientToken.getToken(), params);
        }

    }

    public void sendInviteAcceptedNotification(List<UserToken> senderTokens, Invite invite) {
        String title = "Invitation Accepted";
        String body = invite.getRecipient().getUserName() + " accepted your invitation to join the team " + invite.getTeam().getName() + " !";

        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("body", body);
        params.put("inviteId", invite.getId().toString());

        for (UserToken senderToken : senderTokens) {
            notificationService.sendNotificationToUser(senderToken.getToken(), params);
        }
    }

    public void sendInviteDeclinedNotification(List<UserToken> senderTokens, Invite invite) {
        String title = "Invitation Declined";
        String body = invite.getRecipient().getUserName() + " declined your invitation to join the team " + invite.getTeam().getName() + " .";

        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("body", body);
        params.put("inviteId", invite.getId().toString());

        for (UserToken senderToken : senderTokens) {
            notificationService.sendNotificationToUser(senderToken.getToken(), params);
        }
    }
}
