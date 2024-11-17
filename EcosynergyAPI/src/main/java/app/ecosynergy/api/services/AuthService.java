package app.ecosynergy.api.services;

import app.ecosynergy.api.controllers.UserController;
import app.ecosynergy.api.data.vo.v1.UserVO;
import app.ecosynergy.api.data.vo.v1.security.AccountCredentialsVO;
import app.ecosynergy.api.data.vo.v1.security.TokenVO;
import app.ecosynergy.api.exceptions.InvalidUserDataException;
import app.ecosynergy.api.exceptions.RequiredObjectIsNullException;
import app.ecosynergy.api.exceptions.ResourceAlreadyExistsException;
import app.ecosynergy.api.exceptions.ResourceNotFoundException;
import app.ecosynergy.api.mapper.DozerMapper;
import app.ecosynergy.api.models.User;
import app.ecosynergy.api.repositories.NotificationPreferenceRepository;
import app.ecosynergy.api.repositories.UserRepository;
import app.ecosynergy.api.security.jwt.JwtTokenProvider;
import app.ecosynergy.api.util.ConfirmationCodeGenerator;
import app.ecosynergy.api.util.PasswordUtils;
import app.ecosynergy.api.util.UserPreferenceUtils;
import app.ecosynergy.api.util.ValidationUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class AuthService {
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final EmailService emailService;
    private final NotificationPreferenceRepository notificationPreferenceRepository;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    public AuthService(@Lazy JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager, UserRepository repository, EmailService emailService, NotificationPreferenceRepository notificationPreferenceRepository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.emailService = emailService;
        this.notificationPreferenceRepository = notificationPreferenceRepository;
    }

    public ResponseEntity<?> signIn(AccountCredentialsVO data) {
        try {
            var loginIdentifier = data.getIdentifier();
            var password = data.getPassword();
            User user;

            if(ValidationUtils.isValidEmail(data.getIdentifier())){
                user = repository.findByEmail(loginIdentifier);
            } else {
                user = repository.findByUsername(loginIdentifier);
            }

            if (user != null) {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUserName(), password)
                );
            } else {
                throw new UsernameNotFoundException("Username " + loginIdentifier + " not found");
            }

            var tokenResponse = new TokenVO();
            tokenResponse = tokenProvider.createAccessToken(user.getUserName(), user.getRoles());

            return ResponseEntity.ok(tokenResponse);

        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new BadCredentialsException("Invalid username/email or password supplied!");
        }
    }

    public UserVO signUp(UserVO user) throws MessagingException {
        String currentPassword = user.getPassword();

        if(!ValidationUtils.isValidEmail(user.getEmail())){
            throw new InvalidUserDataException("Invalid email format");
        }

        if(!ValidationUtils.isFullNameValid(user.getFullName())){
            throw new InvalidUserDataException("Full name contains special characters or digits.");
        }

        user.setFullName(ValidationUtils.formatFullName(user.getFullName()));
        user.setUserName(user.getUserName().toLowerCase());
        user.setPassword(PasswordUtils.passwordEncode(currentPassword));
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        boolean isAlreadyExists = repository.existsByUserName(user.getUserName());

        if (isAlreadyExists)
            throw new ResourceAlreadyExistsException("User with username '" + user.getUserName() + "' already exists");

        isAlreadyExists = repository.existsByEmail(user.getEmail());

        if (isAlreadyExists)
            throw new ResourceAlreadyExistsException("User with email '" + user.getEmail() + "' already exists");

        User entity = DozerMapper.parseObject(user, User.class);

        User savedEntity = repository.save(entity);

        notificationPreferenceRepository.saveAll(UserPreferenceUtils.createDefaultPreferences(savedEntity));

        UserVO vo = DozerMapper.parseObject(savedEntity, UserVO.class);
        vo.add(linkTo(methodOn(UserController.class).findById(vo.getKey())).withSelfRel());

        emailService.sendWelcomeEmail(savedEntity);

        return vo;
    }

    public ResponseEntity<?> refreshToken(String username, String refreshToken) {
        var user = repository.findByUsername(username);

        var tokenResponse = new TokenVO();
        if (user != null) {
            tokenResponse = tokenProvider.refreshToken(refreshToken);
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }

        return ResponseEntity.ok(tokenResponse);
    }

    public String sendConfirmationCode(String email) throws MessagingException {
        if (!ValidationUtils.isValidEmail(email)) throw new InvalidUserDataException("Invalid email format");

        User user = repository.findByEmail(email);

        if (user == null) throw new ResourceNotFoundException("Email Not Found");

        String confirmationCode = ConfirmationCodeGenerator.generateCode();

        emailService.sendConfirmationEmail(email, user.getFullName(), confirmationCode);

        return confirmationCode;
    }

    public UserVO forgotPassword(UserVO user) {
        if (user.getEmail() == null || user.getPassword() == null) throw new RequiredObjectIsNullException();

        if(!ValidationUtils.isValidEmail(user.getEmail())){
            throw new InvalidUserDataException("Invalid email format");
        }

        User entity = repository.findByEmail(user.getEmail());

        entity.setPassword(PasswordUtils.passwordEncode(user.getPassword()));

        UserVO vo = DozerMapper.parseObject(repository.save(entity), UserVO.class);
        vo.add(linkTo(methodOn(UserController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }
}
