package app.ecosynergy.api.repositories;

import app.ecosynergy.api.models.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SectorRepository extends JpaRepository<Sector, Long> {
    List<Sector> findAllByOrderByNameAsc();
    Optional<Sector> findByName(String name);
}
