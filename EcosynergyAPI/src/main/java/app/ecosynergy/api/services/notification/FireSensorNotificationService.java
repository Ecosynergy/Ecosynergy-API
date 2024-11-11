package app.ecosynergy.api.services.notification;

import app.ecosynergy.api.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FireSensorNotificationService {

    @Autowired
    private NotificationService notificationService;

    public void sendFireDetectedNotification(Team team) {
        String title = "Fogo Detectado!";
        String body = "Fogo foi detectado pela equipe " + team.getName() + ". Por favor, tome medidas imediatamente!";

        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("body", body);
        params.put("type", "fire");
        params.put("teamId", team.getId().toString());

        team.getTeamMembers().forEach(teamMember -> teamMember.getUser().getTokens().forEach(userToken -> teamMember.getUser().getNotificationPreferences().forEach(notificationPreference -> {
            if(notificationPreference.getPlatform() == userToken.getPlatform() && notificationPreference.isFireDetection()) {
                notificationService.sendNotificationToUser(userToken.getToken(), params);
            }
        })));
    }
}
