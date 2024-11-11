package app.ecosynergy.api.controllers;

import app.ecosynergy.api.data.vo.v1.UserTokenVO;
import app.ecosynergy.api.models.Platform;
import app.ecosynergy.api.services.TokenService;
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
@RequestMapping("/api/token/v1")
@Tag(name = "Token Management", description = "Operations related to FCM token management.")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/save")
    @Operation(summary = "Save or update FCM token", description = "Saves or updates the FCM token for a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FCM token saved successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    public ResponseEntity<String> saveToken(
            @Parameter(description = "FCM token", required = true) @RequestParam String fcmToken,
            @Parameter(description = "Type of the device", required = true) @RequestParam String platform) {
        tokenService.saveOrUpdateToken(fcmToken, Platform.valueOf(platform.toUpperCase()));
        return ResponseEntity.ok("FCM token saved successfully.");
    }

    @PostMapping("/remove")
    @Operation(summary = "Remove FCM token", description = "Removes the FCM token for a specified user and device.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FCM token removed successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    public ResponseEntity<String> removeToken(
            @Parameter(description = "Type of the device", required = true) @RequestParam String platform) {
        tokenService.removeToken(Platform.valueOf(platform.toUpperCase()));
        return ResponseEntity.ok("FCM token removed successfully.");
    }

    @PostMapping("/remove-all")
    @Operation(summary = "Remove all FCM tokens for a user", description = "Removes all FCM tokens associated with a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All FCM tokens removed successfully."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    public ResponseEntity<String> removeAllTokens() {
        tokenService.removeAllTokens();
        return ResponseEntity.ok("All FCM tokens removed successfully.");
    }

    @GetMapping("/get")
    @Operation(summary = "Get FCM token for a user", description = "Retrieves the FCM token for a specified user and device.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token found."),
            @ApiResponse(responseCode = "404", description = "Token not found.")
    })
    public ResponseEntity<List<UserTokenVO>> getUserTokens(
            @Parameter(description = "Type of the device", required = true) @RequestParam String platform) {
        List<UserTokenVO> tokens = tokenService.getUserToken(Platform.valueOf(platform.toUpperCase()));
        return ResponseEntity.ok(tokens);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all FCM tokens for a user", description = "Retrieves all FCM tokens associated with a specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tokens found."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    public ResponseEntity<List<UserTokenVO>> getAllUserTokens() {
        List<UserTokenVO> tokens = tokenService.getAllUserTokens();
        return ResponseEntity.ok(tokens);
    }
}
