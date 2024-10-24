package app.ecosynergy.api.repositories;

import app.ecosynergy.api.models.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    List<UserToken> findByUserId(Long userId);
}
