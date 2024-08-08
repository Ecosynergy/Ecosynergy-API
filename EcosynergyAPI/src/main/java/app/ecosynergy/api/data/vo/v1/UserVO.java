package app.ecosynergy.api.data.vo.v1;

import app.ecosynergy.api.models.Permission;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@JsonPropertyOrder({"id", "username"})
public class UserVO extends RepresentationModel<UserVO> implements UserDetails, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public UserVO(){}

    @JsonProperty("id")
    @Mapping("id")
    private Long key;

    @JsonProperty("username")
    private String userName;

    private String fullName;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String gender;

    private String nationality;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean accountNonExpired;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean accountNonLocked;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean credentialsNonExpired;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean enabled;

    @JsonIgnore
    private List<Permission> permissions;

    @JsonIgnore
    public List<String> getRoles(){
        if(permissions != null){
            List<String> roles = new ArrayList<>();

            for(Permission permission : permissions){
                roles.add(permission.getDescription());
            }

            return roles;
        } else {
            return null;
        }
    }

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

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return userName;
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
        return Objects.equals(getKey(), userVO.getKey()) && Objects.equals(getUserName(), userVO.getUserName()) && Objects.equals(getFullName(), userVO.getFullName()) && Objects.equals(getEmail(), userVO.getEmail()) && Objects.equals(getPassword(), userVO.getPassword()) && Objects.equals(getGender(), userVO.getGender()) && Objects.equals(getNationality(), userVO.getNationality()) && Objects.equals(isAccountNonExpired(), userVO.isAccountNonExpired()) && Objects.equals(isAccountNonLocked(), userVO.isAccountNonLocked()) && Objects.equals(isCredentialsNonExpired(), userVO.isCredentialsNonExpired()) && Objects.equals(isEnabled(), userVO.isEnabled()) && Objects.equals(getPermissions(), userVO.getPermissions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getKey(), getUserName(), getFullName(), getEmail(), getPassword(), getGender(), getNationality(), isAccountNonExpired(), isAccountNonLocked(), isCredentialsNonExpired(), isEnabled(), getPermissions());
    }
}
