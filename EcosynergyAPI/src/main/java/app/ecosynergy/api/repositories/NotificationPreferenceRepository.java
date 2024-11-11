package app.ecosynergy.api.repositories;

import app.ecosynergy.api.models.NotificationPreference;
import app.ecosynergy.api.models.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NotificationPreferenceRepository extends JpaRepository<NotificationPreference, Long> {
    @Query("SELECT n FROM NotificationPreference n WHERE n.user.id = :userId and n.platform = :platform")
    Optional<NotificationPreference> findByUserIdAndPlatform(Long userId, Platform platform);

    List<NotificationPreference> findByUserId(Long userId);
}
