package app.ecosynergy.api.services.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireSensorNotificationService {

    @Autowired
    private NotificationService notificationService;

    public void sendFireDetectedNotification(List<String> userTokens, String location) {
        String title = "Fire Detected!";
        String body = "A fire has been detected at " + location + ". Please take immediate action!";

        for (String userToken : userTokens) {
            notificationService.sendNotificationToUser(userToken, title, body);
        }
    }
}
