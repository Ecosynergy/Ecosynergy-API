package app.ecosynergy.api.repositories;

import app.ecosynergy.api.models.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query(
            value = "SELECT t FROM Team t JOIN FETCH t.teamMembers tm JOIN FETCH tm.user",
            countQuery = "SELECT COUNT(t) FROM Team t"
    )
    Page<Team> findAllWithMembers(Pageable pageable);

    @Query("SELECT t FROM Team t JOIN FETCH t.teamMembers tm JOIN FETCH tm.user WHERE t.id = :id")
    Optional<Team> findByIdWithMembers(@Param("id") Long id);

    @Query("SELECT t FROM Team t JOIN FETCH t.teamMembers tm JOIN FETCH tm.user WHERE t.handle = :handle")
    Optional<Team> findByHandleWithMembers(@Param("handle") String handle);

    @Query("SELECT t FROM Team t JOIN FETCH t.teamMembers tm JOIN FETCH tm.user WHERE t.handle LIKE %:handle%")
    List<Team> findByHandleContaining(@Param("handle") String handle);
}
