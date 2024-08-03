package app.ecosynergy.api.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

@JsonPropertyOrder("id")
public class TeamVO extends RepresentationModel<TeamVO> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    @Mapping("id")
    private Long key;

    private String handle;

    private String name;

    private String description;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private Set<MemberRoleVO> members;

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<MemberRoleVO> getMembers() {
        return members;
    }

    public void setMembers(Set<MemberRoleVO> members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TeamVO teamVO = (TeamVO) o;
        return Objects.equals(getKey(), teamVO.getKey()) && Objects.equals(getHandle(), teamVO.getHandle()) && Objects.equals(getName(), teamVO.getName()) && Objects.equals(getDescription(), teamVO.getDescription()) && Objects.equals(getCreatedAt(), teamVO.getCreatedAt()) && Objects.equals(getUpdatedAt(), teamVO.getUpdatedAt()) && Objects.equals(getMembers(), teamVO.getMembers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getKey(), getHandle(), getName(), getDescription(), getCreatedAt(), getUpdatedAt(), getMembers());
    }
}