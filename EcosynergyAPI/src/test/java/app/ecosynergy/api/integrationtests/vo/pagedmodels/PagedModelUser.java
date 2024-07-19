package app.ecosynergy.api.integrationtests.vo.pagedmodels;

import app.ecosynergy.api.integrationtests.vo.UserVO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;
import java.util.Objects;

@XmlRootElement
public class PagedModelUser {
    @XmlElement(name = "content")
    List<UserVO> content;

    public PagedModelUser() {
    }

    public List<UserVO> getContent() {
        return content;
    }

    public void setContent(List<UserVO> content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PagedModelUser that = (PagedModelUser) o;
        return Objects.equals(getContent(), that.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getContent());
    }
}
