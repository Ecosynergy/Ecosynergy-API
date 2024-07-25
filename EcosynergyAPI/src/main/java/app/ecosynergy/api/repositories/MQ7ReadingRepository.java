package app.ecosynergy.api.repositories;

import app.ecosynergy.api.models.MQ7Reading;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MQ7ReadingRepository extends JpaRepository<MQ7Reading, Long> {
    @Query("SELECT r FROM MQ7Reading r JOIN FETCH r.team WHERE r.id = :id")
    Optional<MQ7Reading> findByIdWithTeam(Long id);

    @Query("SELECT r FROM MQ7Reading r JOIN FETCH r.team t WHERE t.handle = :teamHandle")
    Page<MQ7Reading> findByTeamHandle(String teamHandle, Pageable pageable);

    @Query("SELECT r FROM MQ7Reading r JOIN FETCH r.team")
    Page<MQ7Reading> findAllWithTeam(Pageable pageable);
}
