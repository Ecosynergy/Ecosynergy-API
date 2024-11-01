package app.ecosynergy.api.services.notification;

import app.ecosynergy.api.services.TokenService;
import com.google.firebase.messaging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class NotificationService {

    private static final Logger logger = Logger.getLogger(NotificationService.class.getName());
    private final FirebaseMessaging fcm;
    @Autowired
    private TokenService tokenService;

    public NotificationService(FirebaseMessaging fcm) {
        this.fcm = fcm;
    }

    public void sendNotificationToUser(String firebaseToken, Map<String, String> params) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        StackTraceElement caller = stackTrace[2];

        String type = switch (caller.getClassName().split("\\.")[caller.getClassName().split("\\.").length - 1]) {
            case "InviteService" -> "invite";
            case "FireReadingService" -> "fire";
            case "TeamService" -> "team";
            default -> caller.getClassName().split("\\.")[4];
        };

        Message.Builder messageBuilder = Message.builder()
                .setToken(firebaseToken);

        params.forEach(messageBuilder::putData);

        messageBuilder.putData("type", params.get("type") != null ? params.get("type") : type);

        Message message = messageBuilder.build();

        try {
            String response = fcm.send(message);
            logger.info("Mensagem enviada: " + response);
        } catch (FirebaseMessagingException e) {
            tokenService.removeAllTokens();
            logger.warning(e.getMessage());
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }

    public void sendNotificationToUsers(List<String> firebaseTokens, String title, String body) {
        MulticastMessage message = MulticastMessage.builder()
                .putData("title", title)
                .putData("body", body)
                .addAllTokens(firebaseTokens)
                .build();

        try {
            BatchResponse response = fcm.sendMulticast(message);
            logger.info("Notificações enviadas: " + response.getSuccessCount());

            if (response.getFailureCount() > 0) {
                response.getResponses().forEach(r -> {
                    if (!r.isSuccessful()) {
                        logger.info("Erro ao enviar notificação: " + r.getException());
                    }
                });
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }
}
