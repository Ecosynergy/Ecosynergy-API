package app.ecosynergy.api.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "team_members")
public class TeamMember implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private TeamMemberId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("teamId")
    @JoinColumn(name = "team_id", insertable = false, updatable = false)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    public TeamMember() {
    }

    public TeamMember(TeamMemberId id, Team team, User user, Role role) {
        this.id = id;
        this.team = team;
        this.user = user;
        this.role = role;
    }

    public TeamMemberId getId() {
        return id;
    }

    public void setId(TeamMemberId id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamMember that = (TeamMember) o;
        return Objects.equals(id.getTeamId(), that.id.getTeamId()) && Objects.equals(id.getUserId(), that.id.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id.getTeamId(), id.getUserId());
    }
}

