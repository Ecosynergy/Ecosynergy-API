package app.ecosynergy.api.services;

import app.ecosynergy.api.models.User;
import app.ecosynergy.api.models.UserToken;
import app.ecosynergy.api.repositories.UserRepository;
import app.ecosynergy.api.repositories.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserTokenRepository userTokenRepository;

    public void saveOrUpdateToken(String fcmToken, String deviceType) {
        User user = userService.getCurrentUser();

        UserToken existingToken = user.getTokens().stream()
                .filter(token -> token.getDeviceType().equals(deviceType))
                .findFirst()
                .orElse(null);

        if (existingToken != null) {
            existingToken.setToken(fcmToken);
        } else {
            UserToken newToken = new UserToken();
            newToken.setToken(fcmToken);
            newToken.setDeviceType(deviceType);
            newToken.setUser(user);
            user.getTokens().add(newToken);
        }

        userRepository.save(user);
    }

    public void removeToken(String deviceType) {
        User user = userService.getCurrentUser();

        List<UserToken> tokens = user.getTokens();
        tokens.removeIf(token -> token.getDeviceType().equals(deviceType));

        userRepository.save(user);
    }

    public void removeAllTokens() {
        User user = userService.getCurrentUser();

        user.getTokens().clear();
        userRepository.save(user);
    }

    public List<String> getUserToken(String deviceType) {
        return userTokenRepository.findByUserId(userService.getCurrentUser().getId())
                .stream()
                .filter(token -> token.getDeviceType().equals(deviceType))
                .map(UserToken::getToken)
                .toList();
    }

    public List<UserToken> getAllUserTokens() {
        return userTokenRepository.findByUserId(userService.getCurrentUser().getId());
    }
}
