package app.ecosynergy.api.services.notification;

import app.ecosynergy.api.models.Invite;
import app.ecosynergy.api.models.User;
import app.ecosynergy.api.models.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InvitationNotificationService {

    @Autowired
    private NotificationService notificationService;

    public void sendInviteNotification(User recipient, Invite invite) {
        String title = "Novo Convite para a Equipe!";
        String body = "@" + invite.getSender().getUserName() + " te convidou para se juntar à equipe " + invite.getTeam().getName() + ".";

        Map<String, String> params = setParams(invite, title, body);

        for (UserToken recipientToken : recipient.getTokens()) {
            recipient.getNotificationPreferences().forEach(notificationPreference -> {
                if(notificationPreference.getPlatform() == recipientToken.getPlatform() && notificationPreference.isInviteReceived()) {
                    notificationService.sendNotificationToUser(recipientToken.getToken(), params);
                }
            });
        }
    }

    public void sendInviteAcceptedNotification(User sender, Invite invite) {
        String title = "Convite Aceito";
        String body = "@" + invite.getRecipient().getUserName() + " aceitou seu convite para se juntar à equipe " + invite.getTeam().getName() + "!";

        Map<String, String> params = setParams(invite, title, body);

        for (UserToken senderToken : sender.getTokens()) {
            sender.getNotificationPreferences().forEach(notificationPreference -> {
                if(notificationPreference.getPlatform() == senderToken.getPlatform() && notificationPreference.isInviteStatus()) {
                    notificationService.sendNotificationToUser(senderToken.getToken(), params);
                }
            });
        }
    }

    public void sendInviteDeclinedNotification(User sender, Invite invite) {
        String title = "Convite Recusado";
        String body = "@" + invite.getRecipient().getUserName() + " recusou seu convite para se juntar à equipe " + invite.getTeam().getName() + ".";

        Map<String, String> params = setParams(invite, title, body);

        for (UserToken senderToken : sender.getTokens()) {
            sender.getNotificationPreferences().forEach(notificationPreference -> {
                if(notificationPreference.getPlatform() == senderToken.getPlatform() && notificationPreference.isInviteStatus()) {
                    notificationService.sendNotificationToUser(senderToken.getToken(), params);
                }
            });
        }
    }

    private Map<String, String> setParams(Invite invite, String title, String body) {
        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("body", body);
        params.put("type", "invite");
        params.put("inviteId", invite.getId().toString());
        params.put("status", invite.getStatus().name());

        return params;
    }
}
