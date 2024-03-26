package app.ecosynergy.api.repositories;

import app.ecosynergy.api.models.FireReading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FireReadingRepository extends JpaRepository<FireReading, Long> {

}
