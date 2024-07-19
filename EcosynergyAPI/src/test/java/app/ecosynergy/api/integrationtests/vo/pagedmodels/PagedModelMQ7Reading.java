package app.ecosynergy.api.integrationtests.vo.pagedmodels;

import app.ecosynergy.api.integrationtests.vo.MQ7ReadingVO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;
import java.util.Objects;

@XmlRootElement
public class PagedModelMQ7Reading {
    @XmlElement(name = "content")
    private List<MQ7ReadingVO> content;

    public PagedModelMQ7Reading() {}

    public List<MQ7ReadingVO> getContent() {
        return content;
    }

    public void setContent(List<MQ7ReadingVO> content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PagedModelMQ7Reading that = (PagedModelMQ7Reading) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(content);
    }
}
