package app.ecosynergy.api.models;

import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "notification_preferences")
public class NotificationPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    @Column(name = "fire_detection", nullable = false)
    private boolean fireDetection;

    @Column(name = "fire_notification_interval_minutes", nullable = false)
    private int fireIntervalMinutes = 10;

    @Column(name = "invite_status", nullable = false)
    private boolean inviteStatus;

    @Column(name = "invite_received", nullable = false)
    private boolean inviteReceived;

    @Column(name = "team_goal_reached", nullable = false)
    private boolean teamGoalReached;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;

    public NotificationPreference() {
    }

    public NotificationPreference(Long id, User user, Platform platform, boolean fireDetection, int fireIntervalMinutes, boolean inviteStatus, boolean inviteReceived, boolean teamGoalReached, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.platform = platform;
        this.fireDetection = fireDetection;
        this.fireIntervalMinutes = fireIntervalMinutes;
        this.inviteStatus = inviteStatus;
        this.inviteReceived = inviteReceived;
        this.teamGoalReached = teamGoalReached;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = ZonedDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public boolean isFireDetection() {
        return fireDetection;
    }

    public void setFireDetection(boolean fireDetection) {
        this.fireDetection = fireDetection;
    }

    public int getFireIntervalMinutes() {
        return fireIntervalMinutes;
    }

    public void setFireIntervalMinutes(int fireNotificationIntervalMinutes) {
        this.fireIntervalMinutes = fireNotificationIntervalMinutes;
    }

    public boolean isInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(boolean inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    public boolean isInviteReceived() {
        return inviteReceived;
    }

    public void setInviteReceived(boolean inviteReceived) {
        this.inviteReceived = inviteReceived;
    }

    public boolean isTeamGoalReached() {
        return teamGoalReached;
    }

    public void setTeamGoalReached(boolean teamGoalReached) {
        this.teamGoalReached = teamGoalReached;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NotificationPreference that = (NotificationPreference) o;
        return isFireDetection() == that.isFireDetection() && getFireIntervalMinutes() == that.getFireIntervalMinutes() && isInviteStatus() == that.isInviteStatus() && isInviteReceived() == that.isInviteReceived() && isTeamGoalReached() == that.isTeamGoalReached() && Objects.equals(getId(), that.getId()) && Objects.equals(getUser(), that.getUser()) && getPlatform() == that.getPlatform() && Objects.equals(getCreatedAt(), that.getCreatedAt()) && Objects.equals(getUpdatedAt(), that.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getPlatform(), isFireDetection(), getFireIntervalMinutes(), isInviteStatus(), isInviteReceived(), isTeamGoalReached(), getCreatedAt(), getUpdatedAt());
    }
}
