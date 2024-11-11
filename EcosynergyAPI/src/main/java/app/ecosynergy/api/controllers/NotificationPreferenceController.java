package app.ecosynergy.api.controllers;

import app.ecosynergy.api.data.vo.v1.NotificationPreferenceVO;
import app.ecosynergy.api.models.Platform;
import app.ecosynergy.api.services.notification.preference.NotificationPreferenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications/preferences/v1/")
@Tag(name = "Notification Preferences", description = "Manage notification preferences for different platforms")
public class NotificationPreferenceController {

    @Autowired
    private NotificationPreferenceService notificationPreferenceService;

    @Operation(summary = "Get all notification preferences", description = "Retrieve a list of all notification preferences.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of notification preferences"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<NotificationPreferenceVO>> getPreferences() {
        List<NotificationPreferenceVO> notificationPreferences = notificationPreferenceService.getNotificationPreferences();
        return ResponseEntity.ok(notificationPreferences);
    }

    @Operation(summary = "Get notification preferences by platform", description = "Retrieve notification preferences for a specified platform.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved notification preference for the specified platform"),
            @ApiResponse(responseCode = "404", description = "Notification preference for the specified platform not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/{platform}")
    public ResponseEntity<NotificationPreferenceVO> getPreferencesByPlatform(
            @Parameter(description = "Platform for which to retrieve notification preferences", example = "WEB", required = true)
            @PathVariable String platform) {
        NotificationPreferenceVO notificationPreference = notificationPreferenceService.getNotificationPreferenceByPlatform(Platform.valueOf(platform.toUpperCase()));

        return ResponseEntity.ok(notificationPreference);
    }

    @Operation(summary = "Update notification preferences", description = "Update the notification preferences for a specified platform.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated notification preferences"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Notification preference not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping
    public ResponseEntity<NotificationPreferenceVO> updatePreferences(
            @Parameter(description = "Notification preference data to update", required = true)
            @RequestBody NotificationPreferenceVO notificationPreference) {
        NotificationPreferenceVO notificationPreferenceVO = notificationPreferenceService.updateNotificationPreference(notificationPreference);

        return ResponseEntity.ok(notificationPreferenceVO);
    }
}