package app.ecosynergy.api.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
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
    @Mapping("id")
    private Long key;
    private Double value;
    private ZonedDateTime timestamp;

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
        return Objects.equals(key, mq135ReadingVO.key) && Objects.equals(value, mq135ReadingVO.value) && Objects.equals(timestamp, mq135ReadingVO.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), key, value, timestamp);
    }
}
