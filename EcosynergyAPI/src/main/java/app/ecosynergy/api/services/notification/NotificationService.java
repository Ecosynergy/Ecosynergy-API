package app.ecosynergy.api.services.notification;

import app.ecosynergy.api.services.TokenService;
import com.google.firebase.messaging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class NotificationService {

    private final FirebaseMessaging fcm;

    public NotificationService(FirebaseMessaging fcm) {
        this.fcm = fcm;
    }

    @Autowired
    private TokenService tokenService;

    private static final Logger logger = Logger.getLogger(NotificationService.class.getName());

    public void sendNotificationToUser(String firebaseToken, String title, String body) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        StackTraceElement caller = stackTrace[2];

        String type = switch (caller.getClassName().split("\\.")[caller.getClassName().split("\\.").length - 1]) {
            case "InviteService" -> "invite";
            case "FireReadingService" -> "fire";
            case "TeamService" -> "team";
            default -> caller.getClassName().split("\\.")[4];
        };

        Message message = Message.builder()
                .putData("title", title)
                .putData("body", body)
                .putData("type", "team")
                .setToken(firebaseToken)
                .build();

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
