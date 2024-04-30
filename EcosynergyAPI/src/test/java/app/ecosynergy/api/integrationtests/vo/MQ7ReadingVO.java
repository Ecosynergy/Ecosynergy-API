package app.ecosynergy.api.integrationtests.vo;

import app.ecosynergy.api.integrationtests.deserializers.ZonedDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@XmlRootElement
public class MQ7ReadingVO extends RepresentationModel<MQ7ReadingVO> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private Double value;

    private ZonedDateTime date;

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
        MQ7ReadingVO mq7ReadingVO = (MQ7ReadingVO) o;
        return Objects.equals(id, mq7ReadingVO.id) && Objects.equals(value, mq7ReadingVO.value) && Objects.equals(date, mq7ReadingVO.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, value, date);
    }
}
