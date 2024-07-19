package app.ecosynergy.api.integrationtests.vo.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@XmlRootElement
public class WrapperMQ7ReadingVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("_embedded")
    private MQ7ReadingEmbeddedVO embedded;

    public WrapperMQ7ReadingVO() {}

    public MQ7ReadingEmbeddedVO getEmbedded() {
        return embedded;
    }

    public void setEmbedded(MQ7ReadingEmbeddedVO embedded) {
        this.embedded = embedded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WrapperMQ7ReadingVO that = (WrapperMQ7ReadingVO) o;
        return Objects.equals(embedded, that.embedded);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(embedded);
    }
}
