package app.ecosynergy.api.services.notification;

import app.ecosynergy.api.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
public class FireSensorNotificationService {

    @Autowired
    private NotificationService notificationService;

    public void sendFireDetectedNotification(Team team, ZonedDateTime timestamp) {
        String title = "Fogo Detectado!";
        String body = "Fogo foi detectado pela equipe " + team.getName() + ". Por favor, tome medidas imediatamente!";

        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("body", body);
        params.put("type", "fire");
        params.put("teamId", team.getId().toString());

        long minutesDifference = timestamp != null ? ChronoUnit.MINUTES.between(timestamp, Instant.now()) : 10;

        team.getTeamMembers().forEach(teamMember -> teamMember.getUser().getTokens().forEach(userToken -> teamMember.getUser().getNotificationPreferences().forEach(notificationPreference -> {
            if(notificationPreference.getPlatform() == userToken.getPlatform() && notificationPreference.isFireDetection() && minutesDifference >= notificationPreference.getFireNotificationIntervalMinutes()) {
                notificationService.sendNotificationToUser(userToken.getToken(), params);
            }
        })));
    }
}
