package app.ecosynergy.api.repositories;

import app.ecosynergy.api.models.Team;
import app.ecosynergy.api.models.TeamMember;
import app.ecosynergy.api.models.TeamMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, TeamMemberId> {
    @Query("SELECT tm.team FROM TeamMember tm WHERE tm.user.id = :userId")
    List<Team> findTeamsByUserId(@Param("userId") Long userId);
}
