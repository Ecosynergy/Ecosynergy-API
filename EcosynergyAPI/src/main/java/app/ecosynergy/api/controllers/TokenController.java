package app.ecosynergy.api.controllers;

import app.ecosynergy.api.services.TokenService;
import app.ecosynergy.api.models.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tokens/v1")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/save")
    public ResponseEntity<String> saveToken(
            @RequestParam Long userId,
            @RequestParam String fcmToken,
            @RequestParam String deviceType,
            @RequestParam ZonedDateTime expiresAt) {
        tokenService.saveOrUpdateToken(userId, fcmToken, deviceType, expiresAt);
        return ResponseEntity.ok("FCM token saved successfully.");
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeToken(
            @RequestParam Long userId,
            @RequestParam String deviceType) {
        tokenService.removeToken(userId, deviceType);
        return ResponseEntity.ok("FCM token removed successfully.");
    }

    @PostMapping("/remove-all")
    public ResponseEntity<String> removeAllTokens(@RequestParam Long userId) {
        tokenService.removeAllTokens(userId);
        return ResponseEntity.ok("All FCM tokens removed successfully.");
    }

    @GetMapping("/get")
    public ResponseEntity<String> getUserToken(
            @RequestParam Long userId,
            @RequestParam String deviceType) {
        String token = tokenService.getUserToken(userId, deviceType);
        return ResponseEntity.ok(token != null ? token : "Token not found.");
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserToken>> getAllUserTokens(@RequestParam Long userId) {
        List<UserToken> tokens = tokenService.getAllUserTokens(userId);
        return ResponseEntity.ok(tokens);
    }
}
