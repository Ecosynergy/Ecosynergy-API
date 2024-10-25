package app.ecosynergy.api.services.notification;

import app.ecosynergy.api.models.TeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FireSensorNotificationService {

    @Autowired
    private NotificationService notificationService;

    public void sendFireDetectedNotification(Set<TeamMember> teamMembers, String teamName) {
        String title = "Fire Detected!";
        String body = "A fire has been detected by the team " + teamName + ". Please take immediate action!";

        for (TeamMember teamMember : teamMembers) {
            teamMember.getUser().getTokens().forEach(userToken -> notificationService.sendNotificationToUser(userToken.getToken(), title, body));
        }
    }
}
