package app.ecosynergy.api.services.notification.preference;

import app.ecosynergy.api.data.vo.v1.NotificationPreferenceVO;
import app.ecosynergy.api.exceptions.RequiredObjectIsNullException;
import app.ecosynergy.api.exceptions.ResourceNotFoundException;
import app.ecosynergy.api.mapper.DozerMapper;
import app.ecosynergy.api.models.NotificationPreference;
import app.ecosynergy.api.models.Platform;
import app.ecosynergy.api.models.User;
import app.ecosynergy.api.repositories.NotificationPreferenceRepository;
import app.ecosynergy.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationPreferenceService {
    @Autowired
    private NotificationPreferenceRepository notificationPreferenceRepository;

    @Autowired
    private UserService userService;

    public List<NotificationPreferenceVO> getNotificationPreferences() {
        User currentUser = userService.getCurrentUser();

        List<NotificationPreference> notificationPreferences = notificationPreferenceRepository.findByUserId(currentUser.getId());

        return DozerMapper.parseListObjects(notificationPreferences, NotificationPreferenceVO.class);
    }

    public NotificationPreferenceVO getNotificationPreferenceByPlatform(Platform platform) {
        if(platform == null) throw new RequiredObjectIsNullException();

        User currentUser = userService.getCurrentUser();

        NotificationPreference notificationPreference = notificationPreferenceRepository.findByUserIdAndPlatform(currentUser.getId(), platform)
                .orElseThrow(() -> new ResourceNotFoundException("Notification Preference not found with the given User ID: " + currentUser.getId() + " and the given platform: " + platform.name()));

        return DozerMapper.parseObject(notificationPreference, NotificationPreferenceVO.class);
    }

    public NotificationPreferenceVO updateNotificationPreference(NotificationPreferenceVO notificationPreferenceVO) {
        if(notificationPreferenceVO == null) throw new RequiredObjectIsNullException();

        User currentUser = userService.getCurrentUser();

        NotificationPreference notificationPreference = notificationPreferenceRepository.findByUserIdAndPlatform(currentUser.getId(), notificationPreferenceVO.getPlatform())
                .orElseThrow(() -> new ResourceNotFoundException("Notification Preference not found with the given User ID: " + currentUser.getId() + " and the given platform: " + notificationPreferenceVO.getPlatform()));

        notificationPreference.setFireDetection(notificationPreferenceVO.isFireDetection());
        notificationPreference.setFireIntervalMinutes(notificationPreferenceVO.getFireIntervalMinutes());
        notificationPreference.setInviteStatus(notificationPreferenceVO.isInviteStatus());
        notificationPreference.setInviteReceived(notificationPreferenceVO.isInviteReceived());
        notificationPreference.setTeamGoalReached(notificationPreferenceVO.isTeamGoalReached());

        notificationPreference = notificationPreferenceRepository.save(notificationPreference);

        return DozerMapper.parseObject(notificationPreference, NotificationPreferenceVO.class);
    }
}
