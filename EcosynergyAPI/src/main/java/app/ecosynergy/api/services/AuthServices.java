package app.ecosynergy.api.services;

import app.ecosynergy.api.data.vo.v1.security.AccountCredentialsVO;
import app.ecosynergy.api.data.vo.v1.security.TokenVO;
import app.ecosynergy.api.repositories.UserRepository;
import app.ecosynergy.api.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServices {
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository repository;

    public ResponseEntity<?> signIn(AccountCredentialsVO data){
        try{
            var username = data.getUsername();
            var password = data.getPassword();

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            var user = repository.findByUsername(username);

            var tokenResponse = new TokenVO();

            if(user != null){
                tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException("Username " + username + " not found!");
            }

            return ResponseEntity.ok(tokenResponse);

        } catch (Exception e){
            throw new BadCredentialsException("Invalid username/password supplied!");
        }
    }

    public ResponseEntity<?> refreshToken(String username, String refreshToken){
        var user = repository.findByUsername(username);

        var tokenResponse = new TokenVO();
        if(user != null){
            tokenResponse = tokenProvider.refreshToken(refreshToken);
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }

        return ResponseEntity.ok(tokenResponse);
    }
}
