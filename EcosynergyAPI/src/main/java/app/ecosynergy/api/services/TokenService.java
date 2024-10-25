package app.ecosynergy.api.services;

import app.ecosynergy.api.models.User;
import app.ecosynergy.api.models.UserToken;
import app.ecosynergy.api.repositories.UserRepository;
import app.ecosynergy.api.repositories.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class TokenService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    public void saveOrUpdateToken(Long userId, String fcmToken, String deviceType, ZonedDateTime expiresAt) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserToken existingToken = userTokenRepository.findByUserId(userId)
                .stream()
                .filter(token -> token.getDeviceType().equals(deviceType))
                .findFirst()
                .orElse(null);

        if (existingToken != null) {
            existingToken.setToken(fcmToken);
            existingToken.setExpiresAt(expiresAt);
            userTokenRepository.save(existingToken);
        } else {
            UserToken newToken = new UserToken();
            newToken.setToken(fcmToken);
            newToken.setDeviceType(deviceType);
            newToken.setExpiresAt(expiresAt);
            newToken.setUser(user);
            user.getTokens().add(newToken);
        }

        userRepository.save(user);
    }

    public void removeToken(Long userId, String deviceType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<UserToken> tokens = user.getTokens();
        tokens.removeIf(token -> token.getDeviceType().equals(deviceType));

        userRepository.save(user);
    }

    public void removeAllTokens(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getTokens().clear();
        userRepository.save(user);
    }

    public List<String> getUserToken(Long userId, String deviceType) {
        return userTokenRepository.findByUserId(userId)
                .stream()
                .filter(token -> token.getDeviceType().equals(deviceType))
                .map(UserToken::getToken)
                .toList();
    }

    public List<UserToken> getAllUserTokens(Long userId) {
        return userTokenRepository.findByUserId(userId);
    }
}
