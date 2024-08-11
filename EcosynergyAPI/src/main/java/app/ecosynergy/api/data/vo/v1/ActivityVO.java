package app.ecosynergy.api.data.vo.v1;

import app.ecosynergy.api.data.vo.v1.views.Views;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import com.github.dozermapper.core.Mapping;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@JsonPropertyOrder("id")
public class ActivityVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonView(Views.SectorView.class)
    @JsonProperty("id")
    @Mapping("id")
    private Long key;

    @JsonView(Views.SectorView.class)
    private String name;

    private String sector;

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActivityVO that = (ActivityVO) o;
        return Objects.equals(key, that.key) && Objects.equals(name, that.name) && Objects.equals(sector, that.sector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), key, name, sector);
    }
}
