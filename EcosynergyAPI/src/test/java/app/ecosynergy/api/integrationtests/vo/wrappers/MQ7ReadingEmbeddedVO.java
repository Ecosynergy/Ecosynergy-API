package app.ecosynergy.api.integrationtests.vo.wrappers;

import app.ecosynergy.api.integrationtests.vo.MQ7ReadingVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@XmlRootElement
public class MQ7ReadingEmbeddedVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("mQ7ReadingVOList")
    private List<MQ7ReadingVO> MQ7Readings;

    public MQ7ReadingEmbeddedVO() {}

    public List<MQ7ReadingVO> getMQ7Readings() {
        return MQ7Readings;
    }

    public void setMQ7Readings(List<MQ7ReadingVO> MQ7Readings) {
        this.MQ7Readings = MQ7Readings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MQ7ReadingEmbeddedVO that = (MQ7ReadingEmbeddedVO) o;
        return Objects.equals(MQ7Readings, that.MQ7Readings);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(MQ7Readings);
    }
}
