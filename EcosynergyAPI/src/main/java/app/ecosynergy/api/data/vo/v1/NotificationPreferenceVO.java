package app.ecosynergy.api.data.vo.v1;

import app.ecosynergy.api.models.Platform;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@JsonPropertyOrder("id")
public class NotificationPreferenceVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Mapping("id")
    @JsonProperty("id")
    private Long key;

    private Long userId;

    private Platform platform;

    private boolean fireDetection;
    private int fireNotificationIntervalMinutes;
    private boolean inviteStatus;
    private boolean inviteReceived;
    private boolean teamGoalReached;

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public NotificationPreferenceVO() {
    }

    public NotificationPreferenceVO(Long key, Long userId, Platform platform, boolean fireDetection, int fireNotificationIntervalMinutes, boolean inviteStatus, boolean inviteReceived, boolean teamGoalReached, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.key = key;
        this.userId = userId;
        this.platform = platform;
        this.fireDetection = fireDetection;
        this.fireNotificationIntervalMinutes = fireNotificationIntervalMinutes;
        this.inviteStatus = inviteStatus;
        this.inviteReceived = inviteReceived;
        this.teamGoalReached = teamGoalReached;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long user) {
        this.userId = user;
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

    public int getFireNotificationIntervalMinutes() {
        return fireNotificationIntervalMinutes;
    }

    public void setFireNotificationIntervalMinutes(int fireNotificationIntervalMinutes) {
        this.fireNotificationIntervalMinutes = fireNotificationIntervalMinutes;
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
        NotificationPreferenceVO that = (NotificationPreferenceVO) o;
        return isFireDetection() == that.isFireDetection() && getFireNotificationIntervalMinutes() == that.getFireNotificationIntervalMinutes() && isInviteStatus() == that.isInviteStatus() && isInviteReceived() == that.isInviteReceived() && isTeamGoalReached() == that.isTeamGoalReached() && Objects.equals(getKey(), that.getKey()) && Objects.equals(getUserId(), that.getUserId()) && getPlatform() == that.getPlatform() && Objects.equals(getCreatedAt(), that.getCreatedAt()) && Objects.equals(getUpdatedAt(), that.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getUserId(), getPlatform(), isFireDetection(), getFireNotificationIntervalMinutes(), isInviteStatus(), isInviteReceived(), isTeamGoalReached(), getCreatedAt(), getUpdatedAt());
    }
}
