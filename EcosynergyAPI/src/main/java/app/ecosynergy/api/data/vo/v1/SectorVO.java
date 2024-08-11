package app.ecosynergy.api.data.vo.v1;

import app.ecosynergy.api.data.vo.v1.views.Views;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import com.github.dozermapper.core.Mapping;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@JsonPropertyOrder("id")
public class SectorVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonView(Views.SectorView.class)
    @JsonProperty("id")
    @Mapping("id")
    private Long key;

    @JsonView(Views.SectorView.class)
    private String name;

    @JsonView(Views.SectorView.class)
    private List<ActivityVO> activities;

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

    public List<ActivityVO> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityVO> activities) {
        this.activities = activities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectorVO sectorVO = (SectorVO) o;
        return Objects.equals(key, sectorVO.key) && Objects.equals(name, sectorVO.name) && Objects.equals(activities, sectorVO.activities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, name, activities);
    }
}
