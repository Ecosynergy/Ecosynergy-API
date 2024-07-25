package app.ecosynergy.api.integrationtests.vo;

import jakarta.xml.bind.annotation.XmlRootElement;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@XmlRootElement
public class FireReadingVO extends RepresentationModel<FireReadingVO> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private Boolean isFire;
    private String teamHandle;
    private ZonedDateTime timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return Objects.equals(getId(), that.getId()) && Objects.equals(isFire, that.isFire) && Objects.equals(getTeamHandle(), that.getTeamHandle()) && Objects.equals(getTimestamp(), that.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), isFire, getTeamHandle(), getTimestamp());
    }
}
