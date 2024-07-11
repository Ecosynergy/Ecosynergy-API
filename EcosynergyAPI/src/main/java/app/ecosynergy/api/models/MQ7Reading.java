package app.ecosynergy.api.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "mq7_readings")
public class MQ7Reading implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double value;

    @Column
    private ZonedDateTime timestamp;

    public MQ7Reading() {}

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
        MQ7Reading mq7Reading = (MQ7Reading) o;
        return Objects.equals(id, mq7Reading.id) && Objects.equals(value, mq7Reading.value) && Objects.equals(timestamp, mq7Reading.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, timestamp);
    }
}
