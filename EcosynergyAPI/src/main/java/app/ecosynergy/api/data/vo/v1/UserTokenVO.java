package app.ecosynergy.api.data.vo.v1;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

public class UserTokenVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String token;
    private String deviceType;
    private ZonedDateTime createdAt;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
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
        return Objects.equals(token, that.token) && Objects.equals(deviceType, that.deviceType) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, deviceType, createdAt);
    }
}
