package app.ecosynergy.api.repositories;

import app.ecosynergy.api.models.MQ135Reading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MQ135ReadingRepository extends JpaRepository<MQ135Reading, Long> {

}
