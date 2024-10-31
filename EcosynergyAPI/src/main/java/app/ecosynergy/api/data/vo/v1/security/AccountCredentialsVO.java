package app.ecosynergy.api.data.vo.v1.security;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class AccountCredentialsVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String identifier;
    private String password;

    public AccountCredentialsVO(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }

    public AccountCredentialsVO() {
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountCredentialsVO that = (AccountCredentialsVO) o;
        return Objects.equals(getIdentifier(), that.getIdentifier()) && Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier(), getPassword());
    }
}
