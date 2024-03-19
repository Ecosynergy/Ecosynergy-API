package app.ecosynergy.api.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "mq7readings")
public class MQ7Reading implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double value;

    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MQ7Reading mq7Reading = (MQ7Reading) o;
        return Objects.equals(id, mq7Reading.id) && Objects.equals(value, mq7Reading.value) && Objects.equals(date, mq7Reading.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, date);
    }
}
