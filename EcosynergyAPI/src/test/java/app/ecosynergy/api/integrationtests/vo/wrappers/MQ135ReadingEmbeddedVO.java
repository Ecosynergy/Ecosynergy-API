package app.ecosynergy.api.integrationtests.vo.wrappers;

import app.ecosynergy.api.integrationtests.vo.MQ135ReadingVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@XmlRootElement
public class MQ135ReadingEmbeddedVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("mQ135ReadingVOList")
    private List<MQ135ReadingVO> MQ135Readings;

    public MQ135ReadingEmbeddedVO() {}

    public List<MQ135ReadingVO> getMQ135Readings() {
        return MQ135Readings;
    }

    public void setMQ135Readings(List<MQ135ReadingVO> MQ135Readings) {
        this.MQ135Readings = MQ135Readings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MQ135ReadingEmbeddedVO that = (MQ135ReadingEmbeddedVO) o;
        return Objects.equals(MQ135Readings, that.MQ135Readings);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(MQ135Readings);
    }
}
