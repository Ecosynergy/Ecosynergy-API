package app.ecosynergy.api.integrationtests.vo;

import com.github.dozermapper.core.Mapping;
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

    @Mapping("id")
    private Long id;

    private Boolean isFire;

    private ZonedDateTime date;

    public Long getKey() {
        return id;
    }

    public void setKey(Long id) {
        this.id = id;
    }

    public Boolean getFire() {
        return isFire;
    }

    public void setFire(Boolean fire) {
        isFire = fire;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FireReadingVO that = (FireReadingVO) o;
        return Objects.equals(id, that.id) && Objects.equals(isFire, that.isFire) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, isFire, date);
    }
}
