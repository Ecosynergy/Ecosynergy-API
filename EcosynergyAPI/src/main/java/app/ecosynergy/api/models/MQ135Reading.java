package app.ecosynergy.api.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "mq135_readings")
public class MQ135Reading implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double value;

    private Date date;

    public MQ135Reading() {}

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
        MQ135Reading mq135Reading = (MQ135Reading) o;
        return Objects.equals(id, mq135Reading.id) && Objects.equals(value, mq135Reading.value) && Objects.equals(date, mq135Reading.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, date);
    }
}
