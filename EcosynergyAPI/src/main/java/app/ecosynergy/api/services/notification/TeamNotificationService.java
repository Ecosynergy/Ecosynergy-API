package app.ecosynergy.api.services.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamNotificationService {

    @Autowired
    private NotificationService notificationService;

    public void sendMemberRemovedNotification(String removedMemberToken, String removerName, String teamName) {
        String title = "You were removed from the team";
        String body = removerName + " removed you from the team " + teamName + ".";
        notificationService.sendNotificationToUser(removedMemberToken, title, body);
    }

    public void sendMemberPromotedNotification(String promotedMemberToken, String promoterName, String teamName, String newRole) {
        String title = "You were promoted!";
        String body = promoterName + " promoted you to the role of " + newRole + " in the team " + teamName + ".";
        notificationService.sendNotificationToUser(promotedMemberToken, title, body);
    }

    public void sendGoalAchievedNotification(List<String> memberTokens, String teamName, String goalType) {
        String title = "Goal Achieved!";
        String body = "The team " + teamName + " has achieved the " + goalType + " goal!";

        for (String memberToken : memberTokens) {
            notificationService.sendNotificationToUser(memberToken, title, body);
        }
    }
}
