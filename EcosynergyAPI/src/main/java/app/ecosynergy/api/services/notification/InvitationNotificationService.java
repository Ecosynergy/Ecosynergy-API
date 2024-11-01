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

    private final String type = "invite";

    public void sendInviteNotification(List<UserToken> recipientTokens, Invite invite) {
        String title = "Novo Convite para a Equipe!";
        String body = invite.getSender().getUserName() + " te convidou para se juntar à equipe " + invite.getTeam().getName() + ".";

        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("body", body);
        params.put("type", type);
        params.put("inviteId", invite.getId().toString());

        for (UserToken recipientToken : recipientTokens) {
            notificationService.sendNotificationToUser(recipientToken.getToken(), params);
        }
    }

    public void sendInviteAcceptedNotification(List<UserToken> senderTokens, Invite invite) {
        String title = "Convite Aceito";
        String body = invite.getRecipient().getUserName() + " aceitou seu convite para se juntar à equipe " + invite.getTeam().getName() + "!";

        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("body", body);
        params.put("type", type);
        params.put("inviteId", invite.getId().toString());

        for (UserToken senderToken : senderTokens) {
            notificationService.sendNotificationToUser(senderToken.getToken(), params);
        }
    }

    public void sendInviteDeclinedNotification(List<UserToken> senderTokens, Invite invite) {
        String title = "Convite Recusado";
        String body = invite.getRecipient().getUserName() + " recusou seu convite para se juntar à equipe " + invite.getTeam().getName() + ".";

        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("body", body);
        params.put("type", type);
        params.put("inviteId", invite.getId().toString());

        for (UserToken senderToken : senderTokens) {
            notificationService.sendNotificationToUser(senderToken.getToken(), params);
        }
    }
}
