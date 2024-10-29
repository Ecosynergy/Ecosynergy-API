package app.ecosynergy.api.services;

import app.ecosynergy.api.data.vo.v1.UserTokenVO;
import app.ecosynergy.api.exceptions.RequiredObjectIsNullException;
import app.ecosynergy.api.exceptions.ResourceNotFoundException;
import app.ecosynergy.api.mapper.DozerMapper;
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

    public List<UserTokenVO> getUserToken(String deviceType) {
        List<UserToken> userTokens = userTokenRepository.findByUserId(userService.getCurrentUser().getId())
                .stream()
                .filter(token -> token.getDeviceType().equals(deviceType))
                .toList();

        return DozerMapper.parseListObjects(userTokens, UserTokenVO.class);
    }

    public List<UserTokenVO> getAllUserTokens() {
        List<UserToken> userTokens = userTokenRepository.findByUserId(userService.getCurrentUser().getId());

        return DozerMapper.parseListObjects(userTokens, UserTokenVO.class);
    }

    public UserToken findByToken(String token) {
        if(token == null) throw new RequiredObjectIsNullException();

        return userTokenRepository.findByToken(token).orElseThrow(() -> new ResourceNotFoundException("FCM Token not found"));
    }
}
