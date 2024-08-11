package app.ecosynergy.api.repositories;

import app.ecosynergy.api.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findAllByOrderByNameAsc();

    @Query("SELECT a FROM Activity a WHERE a.sector.id = :sectorId ORDER BY a.name ASC")
    List<Activity> findActivitiesBySectorId(@Param("sectorId") Long sectorId);

    Optional<Activity> findByName(String name);
}
