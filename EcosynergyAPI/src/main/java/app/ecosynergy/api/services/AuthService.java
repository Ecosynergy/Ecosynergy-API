package app.ecosynergy.api.services;

import app.ecosynergy.api.controllers.UserController;
import app.ecosynergy.api.data.vo.v1.UserVO;
import app.ecosynergy.api.data.vo.v1.security.AccountCredentialsVO;
import app.ecosynergy.api.data.vo.v1.security.TokenVO;
import app.ecosynergy.api.exceptions.ResourceAlreadyExistsException;
import app.ecosynergy.api.mapper.DozerMapper;
import app.ecosynergy.api.models.User;
import app.ecosynergy.api.repositories.UserRepository;
import app.ecosynergy.api.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
import java.util.Locale;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class AuthService {
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;

    @Autowired
    public AuthService(@Lazy JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager, UserRepository repository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.repository = repository;
    }

    public ResponseEntity<?> signIn(AccountCredentialsVO data){
        try{
            var loginIdentifier = data.getIdentifier();
            var password = data.getPassword();
            User user;

            try{
                user = repository.findByUsername(loginIdentifier);

                if(user != null){
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(user.getUserName(), password)
                    );
                } else {
                    throw new UsernameNotFoundException("Username " + loginIdentifier + " not found");
                }
            } catch (UsernameNotFoundException ex){
                user = repository.findByEmail(loginIdentifier);
                if(user != null){
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(user.getUserName(), password)
                    );
                }
            }

            var tokenResponse = new TokenVO();

            if(user != null){
                tokenResponse = tokenProvider.createAccessToken(user.getUserName(), user.getRoles());
            } else {
                throw new UsernameNotFoundException("User not found with username/email: " + loginIdentifier);
            }

            return ResponseEntity.ok(tokenResponse);

        } catch (Exception e){
            throw new BadCredentialsException("Invalid username/email or password supplied!");
        }
    }

    public UserVO signUp(UserVO user){
        String currentPassword = user.getPassword();

        user.setUserName(user.getUserName().toLowerCase(Locale.ROOT));
        user.setPassword(passwordEncode(currentPassword));
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        boolean isAlreadyExists = repository.existsByUserName(user.getUserName());

        if(isAlreadyExists) throw new ResourceAlreadyExistsException("User with username '" + user.getUserName() + "' already exists");

        isAlreadyExists = repository.existsByEmail(user.getEmail());

        if(isAlreadyExists) throw new ResourceAlreadyExistsException("User with email '" + user.getEmail() + "' already exists");

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
