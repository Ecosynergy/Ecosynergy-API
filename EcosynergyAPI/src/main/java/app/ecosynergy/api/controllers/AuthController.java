package app.ecosynergy.api.controllers;

import app.ecosynergy.api.data.vo.v1.security.AccountCredentialsVO;
import app.ecosynergy.api.services.AuthServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthServices service;

    @Operation(summary = "Authenticates a user and returns a token")
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody AccountCredentialsVO data){
        if(checkIfParamsIsNotNull(data))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");

        var token = service.signIn(data);
        if(token == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid client request");
        else
            return token;
    }

    @Operation(summary = "Refresh token for authenticated user and returns a token")
    @PutMapping("/refresh/{username}")
    public ResponseEntity<?> refreshToken(@PathVariable("username") String username, @RequestHeader("Authorization") String refreshToken){
        if(checkIfParamsIsNotNull(username, refreshToken))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");

        var token = service.refreshToken(username, refreshToken);

        if(token == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid client request");
        } else {
            return token;
        }
    }

    private static boolean checkIfParamsIsNotNull(AccountCredentialsVO data) {
        return data == null || data.getUsername() == null || data.getUsername().isBlank() ||
                data.getPassword() == null || data.getPassword().isBlank();
    }

    private static boolean checkIfParamsIsNotNull(String username, String refreshToken) {
        return refreshToken == null || refreshToken.isBlank() || username == null || username.isBlank();
    }
}
