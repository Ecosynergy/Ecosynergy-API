package app.ecosynergy.api.repositories;

import app.ecosynergy.api.models.MQ135Reading;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MQ135ReadingRepository extends JpaRepository<MQ135Reading, Long> {
    @Query("SELECT r FROM MQ135Reading r JOIN FETCH r.team WHERE r.id = :id")
    Optional<MQ135Reading> findByIdWithTeam(Long id);

    @Query("SELECT r FROM MQ135Reading r JOIN FETCH r.team")
    Page<MQ135Reading> findAllWithTeam(Pageable pageable);
}
