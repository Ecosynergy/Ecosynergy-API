package app.ecosynergy.api.data.vo.v1;

import app.ecosynergy.api.models.Permission;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@JsonPropertyOrder("id")
public class UserVO extends RepresentationModel<UserVO> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public UserVO(){}

    @JsonProperty("id")
    private Long key;

    @JsonIgnore
    private String userName;

    private String fullName;

    private String email;

    @JsonIgnore
    private String password;

    private String gender;

    private String nationality;

    private Boolean accountNonExpired;

    private Boolean accountNonLocked;

    private Boolean credentialsNonExpired;

    private Boolean enabled;

    private List<Permission> permissions;

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserVO vo = (UserVO) o;
        return Objects.equals(key, vo.key) && Objects.equals(userName, vo.userName) && Objects.equals(fullName, vo.fullName) && Objects.equals(email, vo.email) && Objects.equals(password, vo.password) && Objects.equals(gender, vo.gender) && Objects.equals(nationality, vo.nationality) && Objects.equals(accountNonExpired, vo.accountNonExpired) && Objects.equals(accountNonLocked, vo.accountNonLocked) && Objects.equals(credentialsNonExpired, vo.credentialsNonExpired) && Objects.equals(enabled, vo.enabled) && Objects.equals(permissions, vo.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), key, userName, fullName, email, password, gender, nationality, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled, permissions);
    }
}
