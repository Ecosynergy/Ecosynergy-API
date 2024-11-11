package app.ecosynergy.api.controllers;

import app.ecosynergy.api.data.vo.v1.NotificationPreferenceVO;
import app.ecosynergy.api.models.Platform;
import app.ecosynergy.api.services.notification.preference.NotificationPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications/preferences/v1/")
public class NotificationPreferenceController {
    @Autowired
    private NotificationPreferenceService notificationPreferenceService;

    @GetMapping
    public ResponseEntity<List<NotificationPreferenceVO>> getPreferences() {
        List<NotificationPreferenceVO> notificationPreferences = notificationPreferenceService.getNotificationPreferences();
        return ResponseEntity.ok(notificationPreferences);
    }

    @GetMapping(value = "/{platform}")
    public ResponseEntity<NotificationPreferenceVO> getPreferencesByPlatform(@PathVariable String platform) {
        NotificationPreferenceVO notificationPreference = notificationPreferenceService.getNotificationPreferenceByPlatform(Platform.valueOf(platform.toUpperCase()));

        return ResponseEntity.ok(notificationPreference);
    }

    @PutMapping
    public ResponseEntity<NotificationPreferenceVO> updatePreferences(@RequestBody NotificationPreferenceVO notificationPreference) {
        NotificationPreferenceVO notificationPreferenceVO = notificationPreferenceService.updateNotificationPreference(notificationPreference);

        return ResponseEntity.ok(notificationPreferenceVO);
    }
}
