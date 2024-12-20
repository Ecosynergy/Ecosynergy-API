package app.ecosynergy.api.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "teams", uniqueConstraints = @UniqueConstraint(columnNames = "handle"))
public class Team implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "handle", unique = true, nullable = false)
    private String handle;

    @Column
    private String name;

    @Column
    private String description;

    @Transient
    private Sector sector;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @Column(name = "daily_goal")
    private BigDecimal dailyGoal;

    @Column(name = "weekly_goal")
    private BigDecimal weeklyGoal;

    @Column(name = "monthly_goal")
    private BigDecimal monthlyGoal;

    @Column(name = "annual_goal")
    private BigDecimal annualGoal;

    @Column(name = "time_zone")
    private ZoneId timeZone;

    @Column(name = "created_at", nullable = false, updatable = false)
    private ZonedDateTime createdAt = ZonedDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt = ZonedDateTime.now();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<TeamMember> teamMembers = new HashSet<>();

    public Team() {
    }

    public Team(Long id, String handle, String name, String description, ZonedDateTime createdAt, ZonedDateTime updatedAt, Set<TeamMember> teamMembers) {
        this.id = id;
        this.handle = handle;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.teamMembers = teamMembers;
    }

    @PrePersist
    public void prePersist() {
        ZonedDateTime now = ZonedDateTime.now();

        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = ZonedDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Sector getSector() {
        return activity != null ? activity.getSector() : null;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public BigDecimal getDailyGoal() {
        return dailyGoal;
    }

    public void setDailyGoal(BigDecimal dailyGoal) {
        this.dailyGoal = dailyGoal;
    }

    public BigDecimal getWeeklyGoal() {
        return weeklyGoal;
    }

    public void setWeeklyGoal(BigDecimal weeklyGoal) {
        this.weeklyGoal = weeklyGoal;
    }

    public BigDecimal getMonthlyGoal() {
        return monthlyGoal;
    }

    public void setMonthlyGoal(BigDecimal monthlyGoal) {
        this.monthlyGoal = monthlyGoal;
    }

    public BigDecimal getAnnualGoal() {
        return annualGoal;
    }

    public void setAnnualGoal(BigDecimal annualGoal) {
        this.annualGoal = annualGoal;
    }

    public ZoneId getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(ZoneId timeZone) {
        this.timeZone = timeZone;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(Set<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id) && Objects.equals(handle, team.handle) && Objects.equals(name, team.name) && Objects.equals(description, team.description) && Objects.equals(sector, team.sector) && Objects.equals(activity, team.activity) && Objects.equals(dailyGoal, team.dailyGoal) && Objects.equals(weeklyGoal, team.weeklyGoal) && Objects.equals(monthlyGoal, team.monthlyGoal) && Objects.equals(annualGoal, team.annualGoal) && Objects.equals(timeZone, team.timeZone) && Objects.equals(createdAt, team.createdAt) && Objects.equals(updatedAt, team.updatedAt) && Objects.equals(teamMembers, team.teamMembers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, handle, name, description, sector, activity, dailyGoal, weeklyGoal, monthlyGoal, annualGoal, timeZone, createdAt, updatedAt, teamMembers);
    }
}
