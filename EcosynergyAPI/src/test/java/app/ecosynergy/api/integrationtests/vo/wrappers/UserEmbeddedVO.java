package app.ecosynergy.api.integrationtests.vo.wrappers;

import app.ecosynergy.api.integrationtests.vo.UserVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@XmlRootElement
public class UserEmbeddedVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("userVOList")
    private List<UserVO> userVOList;

    public UserEmbeddedVO() {}

    public List<UserVO> getUserVOList() {
        return userVOList;
    }

    public void setUserVOList(List<UserVO> userVOList) {
        this.userVOList = userVOList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEmbeddedVO that = (UserEmbeddedVO) o;
        return Objects.equals(getUserVOList(), that.getUserVOList());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUserVOList());
    }
}
