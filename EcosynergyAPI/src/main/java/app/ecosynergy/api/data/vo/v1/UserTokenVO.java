package app.ecosynergy.api.data.vo.v1;

import app.ecosynergy.api.models.Platform;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

public class UserTokenVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String token;
    private Platform platform;
    private ZonedDateTime createdAt;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTokenVO that = (UserTokenVO) o;
        return Objects.equals(getToken(), that.getToken()) && getPlatform() == that.getPlatform() && Objects.equals(getCreatedAt(), that.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getToken(), getPlatform(), getCreatedAt());
    }
}
