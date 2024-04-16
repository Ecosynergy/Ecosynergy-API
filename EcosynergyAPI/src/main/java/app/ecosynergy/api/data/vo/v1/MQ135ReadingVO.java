package app.ecosynergy.api.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@JsonPropertyOrder("id")
public class MQ135ReadingVO extends RepresentationModel<MQ135ReadingVO> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Long key;
    private Double value;
    private ZonedDateTime date;

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
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
        MQ135ReadingVO mq135ReadingVO = (MQ135ReadingVO) o;
        return Objects.equals(key, mq135ReadingVO.key) && Objects.equals(value, mq135ReadingVO.value) && Objects.equals(date, mq135ReadingVO.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), key, value, date);
    }
}
