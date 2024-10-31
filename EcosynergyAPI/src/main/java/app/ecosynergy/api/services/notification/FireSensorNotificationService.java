package app.ecosynergy.api.services.notification;

import app.ecosynergy.api.models.Team;
import app.ecosynergy.api.models.TeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class FireSensorNotificationService {

    @Autowired
    private NotificationService notificationService;

    public void sendFireDetectedNotification(Set<TeamMember> teamMembers, Team team) {
        String title = "Fogo Detectado!";
        String body = "Fogo foi detectado pela equipe " + team.getName() + ". Por favor, tome medidas imediatamente!";

        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("body", body);
        params.put("teamId", team.getId().toString());

        for (TeamMember teamMember : teamMembers) {
            teamMember.getUser().getTokens().forEach(userToken -> notificationService.sendNotificationToUser(userToken.getToken(), params));
        }
    }
}
