package app.ecosynergy.api.services.notification;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class NotificationService {

    private static final Logger logger = Logger.getLogger(NotificationService.class.getName());

    public void sendNotificationToUser(String firebaseToken, String title, String body) {
        Message message = Message.builder()
                .putData("title", title)
                .putData("body", body)
                .setToken(firebaseToken)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            logger.info("Mensagem enviada: " + response);
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
            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
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
