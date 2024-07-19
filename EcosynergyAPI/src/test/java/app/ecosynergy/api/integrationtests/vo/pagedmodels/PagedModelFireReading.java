package app.ecosynergy.api.integrationtests.vo.pagedmodels;

import app.ecosynergy.api.integrationtests.vo.FireReadingVO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;
import java.util.Objects;

@XmlRootElement
public class PagedModelFireReading {
    @XmlElement(name = "content")
    private List<FireReadingVO> content;

    public PagedModelFireReading() {}

    public List<FireReadingVO> getContent() {
        return content;
    }

    public void setContent(List<FireReadingVO> content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PagedModelFireReading that = (PagedModelFireReading) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(content);
    }
}
