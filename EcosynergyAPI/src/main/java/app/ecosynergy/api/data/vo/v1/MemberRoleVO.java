package app.ecosynergy.api.data.vo.v1;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class MemberRoleVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String role;

    public MemberRoleVO() {
    }

    public MemberRoleVO(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberRoleVO that = (MemberRoleVO) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getRole(), that.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRole());
    }
}
