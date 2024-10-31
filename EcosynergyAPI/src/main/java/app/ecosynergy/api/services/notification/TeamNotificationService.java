package app.ecosynergy.api.services.notification;

import app.ecosynergy.api.models.Team;
import app.ecosynergy.api.models.TeamMember;
import app.ecosynergy.api.models.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static app.ecosynergy.api.models.Role.ADMINISTRATOR;
import static app.ecosynergy.api.models.Role.COMMON_USER;

@Service
public class TeamNotificationService {

    @Autowired
    private NotificationService notificationService;

    public void sendMemberAddedNotification(List<UserToken> newMemberTokens, Team team) {
        String title = "Welcome to the team!";
        String body = "You have been added to the team " + team.getName() + ". We're excited to have you with us!";

        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("body", body);
        params.put("teamId", team.getId().toString());

        for (UserToken newMemberToken : newMemberTokens) {
            notificationService.sendNotificationToUser(newMemberToken.getToken(), params);
        }
    }

    public void sendMemberRemovedNotification(List<UserToken> removedMemberTokens, String removerName, Team team) {
        String title = "You were removed from the team";
        String body = removerName + " removed you from the team " + team.getName() + ".";

        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("body", body);

        for (UserToken removedMemberToken : removedMemberTokens) {
            notificationService.sendNotificationToUser(removedMemberToken.getToken(), params);
        }
    }

    public void sendMemberPromotedNotification(List<UserToken> promotedMemberTokens, String promoterName, Team team, String newRole) {
        String title = "You were promoted!";
        String body = promoterName + " promoted you to the role of " + (Objects.equals(newRole, COMMON_USER.name()) ? "COMMON USER" : ADMINISTRATOR.name()) + " in the team " + team.getName() + ".";

        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("body", body);
        params.put("teamId", team.getId().toString());

        for (UserToken promotedMemberToken : promotedMemberTokens) {
            notificationService.sendNotificationToUser(promotedMemberToken.getToken(), params);
        }
    }

    public void sendGoalAchievedNotification(Set<TeamMember> members, Team team, String goalType) {
        String title = "Goal Achieved!";
        String body = "The team " + team.getName() + " has achieved the " + goalType + " goal!";

        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("body", body);
        params.put("teamId", team.getId().toString());

        for (TeamMember teamMember : members) {
            teamMember.getUser().getTokens().forEach(memberToken -> notificationService.sendNotificationToUser(memberToken.getToken(), params));
        }
    }
}
