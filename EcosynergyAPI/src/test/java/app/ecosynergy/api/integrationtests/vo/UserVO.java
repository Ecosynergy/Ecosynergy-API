package app.ecosynergy.api.integrationtests.vo;

import app.ecosynergy.api.models.Permission;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "UserVO")
public class UserVO extends RepresentationModel<UserVO> implements UserDetails, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public UserVO(){}

    private Long id;

    private String username;

    private String fullName;

    private String email;

    private String password;

    private String gender;

    private String nationality;

    private ZoneId timeZone;

    private Boolean accountNonExpired;

    private Boolean accountNonLocked;

    private Boolean credentialsNonExpired;

    private Boolean enabled;

    @JsonIgnore
    private List<Permission> permissions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
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

    @JsonGetter("timeZone")
    public ZoneId getTimeZone() {
        return timeZone;
    }

    @JsonSetter("timeZone")
    public void setTimeZone(ZoneId timeZone) {
        this.timeZone = timeZone;
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
        UserVO userVO = (UserVO) o;
        return Objects.equals(getId(), userVO.getId()) && Objects.equals(getUsername(), userVO.getUsername()) && Objects.equals(getFullName(), userVO.getFullName()) && Objects.equals(getEmail(), userVO.getEmail()) && Objects.equals(getPassword(), userVO.getPassword()) && Objects.equals(getGender(), userVO.getGender()) && Objects.equals(getNationality(), userVO.getNationality()) && Objects.equals(getTimeZone(), userVO.getTimeZone()) && Objects.equals(isAccountNonExpired(), userVO.isAccountNonExpired()) && Objects.equals(isAccountNonLocked(), userVO.isAccountNonLocked()) && Objects.equals(isCredentialsNonExpired(), userVO.isCredentialsNonExpired()) && Objects.equals(isEnabled(), userVO.isEnabled()) && Objects.equals(getPermissions(), userVO.getPermissions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getUsername(), getFullName(), getEmail(), getPassword(), getGender(), getNationality(), getTimeZone(), isAccountNonExpired(), isAccountNonLocked(), isCredentialsNonExpired(), isEnabled(), getPermissions());
    }
}
