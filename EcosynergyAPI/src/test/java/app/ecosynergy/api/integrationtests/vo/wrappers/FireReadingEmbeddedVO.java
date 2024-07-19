package app.ecosynergy.api.integrationtests.vo.wrappers;

import app.ecosynergy.api.integrationtests.vo.FireReadingVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@XmlRootElement
public class FireReadingEmbeddedVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("fireReadingVOList")
    private List<FireReadingVO> fireReadingVOList;

    public FireReadingEmbeddedVO() {}

    public List<FireReadingVO> getFireReadingVOList() {
        return fireReadingVOList;
    }

    public void setFireReadingVOList(List<FireReadingVO> fireReadingVOList) {
        this.fireReadingVOList = fireReadingVOList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FireReadingEmbeddedVO that = (FireReadingEmbeddedVO) o;
        return Objects.equals(fireReadingVOList, that.fireReadingVOList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(fireReadingVOList);
    }
}
