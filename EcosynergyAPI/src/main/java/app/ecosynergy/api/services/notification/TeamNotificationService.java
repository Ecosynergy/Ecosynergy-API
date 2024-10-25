package app.ecosynergy.api.services.notification;

import app.ecosynergy.api.models.TeamMember;
import app.ecosynergy.api.models.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TeamNotificationService {

    @Autowired
    private NotificationService notificationService;

    public void sendMemberAddedNotification(List<UserToken> newMemberTokens, String teamName) {
        String title = "Welcome to the team!";
        String body = "You have been added to the team " + teamName + ". We're excited to have you with us!";

        for (UserToken newMemberToken : newMemberTokens) {
            notificationService.sendNotificationToUser(newMemberToken.getToken(), title, body);
        }
    }

    public void sendMemberRemovedNotification(List<UserToken> removedMemberTokens, String removerName, String teamName) {
        String title = "You were removed from the team";
        String body = removerName + " removed you from the team " + teamName + ".";

        for (UserToken removedMemberToken : removedMemberTokens) {
            notificationService.sendNotificationToUser(removedMemberToken.getToken(), title, body);
        }
    }

    public void sendMemberPromotedNotification(List<UserToken> promotedMemberTokens, String promoterName, String teamName, String newRole) {
        String title = "You were promoted!";
        String body = promoterName + " promoted you to the role of " + newRole + " in the team " + teamName + ".";

        for (UserToken promotedMemberToken : promotedMemberTokens) {
            notificationService.sendNotificationToUser(promotedMemberToken.getToken(), title, body);
        }
    }

    public void sendGoalAchievedNotification(Set<TeamMember> members, String teamName, String goalType) {
        String title = "Goal Achieved!";
        String body = "The team " + teamName + " has achieved the " + goalType + " goal!";

        for (TeamMember teamMember : members) {
            teamMember.getUser().getTokens().forEach(memberToken -> notificationService.sendNotificationToUser(memberToken.getToken(), title, body));
        }
    }
}
