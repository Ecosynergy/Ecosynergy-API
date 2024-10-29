package app.ecosynergy.api.repositories;

import app.ecosynergy.api.models.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    List<UserToken> findByUserId(Long userId);

    Optional<UserToken> findByToken(String token);
}
