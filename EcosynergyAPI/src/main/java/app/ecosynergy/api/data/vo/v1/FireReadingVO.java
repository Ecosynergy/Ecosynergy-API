package app.ecosynergy.api.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@JsonPropertyOrder({"id", "fire"})
public class FireReadingVO extends RepresentationModel<FireReadingVO> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    @Mapping("id")
    private Long key;

    private String teamHandle;

    @JsonProperty("fire")
    private Boolean isFire;

    private ZonedDateTime timestamp;

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getTeamHandle() {
        return teamHandle;
    }

    public void setTeamHandle(String teamHandle) {
        this.teamHandle = teamHandle;
    }

    public Boolean getFire() {
        return isFire;
    }

    public void setFire(Boolean fire) {
        isFire = fire;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FireReadingVO that = (FireReadingVO) o;
        return Objects.equals(getKey(), that.getKey()) && Objects.equals(getTeamHandle(), that.getTeamHandle()) && Objects.equals(isFire, that.isFire) && Objects.equals(getTimestamp(), that.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getKey(), getTeamHandle(), isFire, getTimestamp());
    }
}
