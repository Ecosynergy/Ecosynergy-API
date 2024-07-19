package app.ecosynergy.api.integrationtests.vo;

import jakarta.xml.bind.annotation.XmlRootElement;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@XmlRootElement
public class MQ135ReadingVO extends RepresentationModel<MQ135ReadingVO> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private Double value;
    private ZonedDateTime timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
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
        MQ135ReadingVO mq135ReadingVO = (MQ135ReadingVO) o;
        return Objects.equals(id, mq135ReadingVO.id) && Objects.equals(value, mq135ReadingVO.value) && Objects.equals(timestamp, mq135ReadingVO.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, value, timestamp);
    }
}
