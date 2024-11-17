package app.ecosynergy.api.util;

import app.ecosynergy.api.models.NotificationPreference;
import app.ecosynergy.api.models.Platform;
import app.ecosynergy.api.models.User;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserPreferenceUtils {
    public static List<NotificationPreference> createDefaultPreferences(User user) {
        List<NotificationPreference> notificationPreferences = new ArrayList<>();
        for (Platform platform : Platform.values()) {
            NotificationPreference preference = new NotificationPreference();
            preference.setUser(user);
            preference.setPlatform(platform);
            preference.setFireDetection(true);
            preference.setFireIntervalMinutes(10);
            preference.setInviteStatus(true);
            preference.setInviteReceived(true);
            preference.setTeamGoalReached(true);
            preference.setCreatedAt(ZonedDateTime.now());
            preference.setUpdatedAt(ZonedDateTime.now());

            notificationPreferences.add(preference);
        }

        return notificationPreferences;
    }
}
