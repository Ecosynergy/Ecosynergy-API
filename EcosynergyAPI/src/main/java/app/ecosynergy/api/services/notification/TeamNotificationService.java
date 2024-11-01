package app.ecosynergy.api.services.notification;

import app.ecosynergy.api.models.Team;
import app.ecosynergy.api.models.TeamMember;
import app.ecosynergy.api.models.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static app.ecosynergy.api.models.Role.COMMON_USER;

@Service
public class TeamNotificationService {

    @Autowired
    private NotificationService notificationService;

    public void sendMemberAddedNotification(List<UserToken> newMemberTokens, Team team) {
        String title = "Bem-vindo à equipe!";
        String body = "Você entrou na equipe " + team.getName() + ". Estamos animados por tê-lo conosco!";

        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("body", body);
        params.put("teamId", team.getId().toString());

        for (UserToken newMemberToken : newMemberTokens) {
            notificationService.sendNotificationToUser(newMemberToken.getToken(), params);
        }
    }

    public void sendMemberRemovedNotification(List<UserToken> removedMemberTokens, String removerName, Team team) {
        String title = "Você foi removido da equipe";
        String body = removerName + " te removeu da equipe " + team.getName() + ".";

        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("body", body);

        for (UserToken removedMemberToken : removedMemberTokens) {
            notificationService.sendNotificationToUser(removedMemberToken.getToken(), params);
        }
    }

    public void sendMemberPromotedNotification(List<UserToken> promotedMemberTokens, String promoterName, Team team, String newRole) {
        String title = "Você foi promovido!";
        String body = promoterName + " te promoveu para o cargo de " + (Objects.equals(newRole, COMMON_USER.name()) ? "USUÁRIO COMUM" : "ADMINISTRADOR") + " na equipe " + team.getName() + ".";

        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("body", body);
        params.put("teamId", team.getId().toString());

        for (UserToken promotedMemberToken : promotedMemberTokens) {
            notificationService.sendNotificationToUser(promotedMemberToken.getToken(), params);
        }
    }

    public void sendGoalAchievedNotification(Set<TeamMember> members, Team team, String goalType) {
        String title = "Meta Atingida!";
        String body = "A equipe " + team.getName() + " atingiu a meta " + goalType + "!";

        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("body", body);
        params.put("teamId", team.getId().toString());

        for (TeamMember teamMember : members) {
            teamMember.getUser().getTokens().forEach(memberToken -> notificationService.sendNotificationToUser(memberToken.getToken(), params));
        }
    }
}