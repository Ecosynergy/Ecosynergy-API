package app.ecosynergy.api.services;

import app.ecosynergy.api.controllers.UserController;
import app.ecosynergy.api.data.vo.v1.UserVO;
import app.ecosynergy.api.data.vo.v1.security.AccountCredentialsVO;
import app.ecosynergy.api.data.vo.v1.security.TokenVO;
import app.ecosynergy.api.mapper.DozerMapper;
import app.ecosynergy.api.models.User;
import app.ecosynergy.api.repositories.UserRepository;
import app.ecosynergy.api.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class AuthServices {
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

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

    public UserVO signUp(UserVO user){
        String currentPassword = user.getPassword();

        user.setPassword(passwordEncode(currentPassword));
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        User entity = repository.save(DozerMapper.parseObject(user, User.class));

        UserVO vo = DozerMapper.parseObject(entity, UserVO.class);

        vo.add(linkTo(methodOn(UserController.class).findById(vo.getKey())).withSelfRel());
        return vo;
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

    private String passwordEncode(String password){
        Map<String, PasswordEncoder> encoders = new HashMap<>();

        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder(
                "",
                8,
                185000,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256
        );

        encoders.put("pbkdf2", pbkdf2PasswordEncoder);
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2PasswordEncoder);

        return passwordEncoder.encode(password);
    }
}
