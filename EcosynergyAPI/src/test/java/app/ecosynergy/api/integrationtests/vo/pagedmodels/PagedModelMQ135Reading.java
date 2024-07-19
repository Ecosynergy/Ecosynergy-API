package app.ecosynergy.api.integrationtests.vo.pagedmodels;

import app.ecosynergy.api.integrationtests.vo.MQ135ReadingVO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;
import java.util.Objects;

@XmlRootElement
public class PagedModelMQ135Reading {
    @XmlElement(name = "content")
    private List<MQ135ReadingVO> content;

    public PagedModelMQ135Reading() {
    }

    public List<MQ135ReadingVO> getContent() {
        return content;
    }

    public void setContent(List<MQ135ReadingVO> content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PagedModelMQ135Reading that = (PagedModelMQ135Reading) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(content);
    }
}
