package app.ecosynergy.api.integrationtests.vo.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@XmlRootElement
public class WrapperUserVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("_embedded")
    private UserEmbeddedVO embedded;

    public UserEmbeddedVO getEmbedded() {
        return embedded;
    }

    public void setEmbedded(UserEmbeddedVO embedded) {
        this.embedded = embedded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WrapperUserVO that = (WrapperUserVO) o;
        return Objects.equals(getEmbedded(), that.getEmbedded());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEmbedded());
    }
}
