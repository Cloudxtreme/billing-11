package com.elstele.bill.domain;


import com.elstele.bill.domain.common.CommonDomainBean;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="LocalUsers")
public class LocalUser extends CommonDomainBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        if (!(o instanceof LocalUser)) return false;

        LocalUser localUser = (LocalUser) o;

        if (!getId().equals(localUser.getId())) return false;
        if (!password.equals(localUser.password)) return false;
        if (!username.equals(localUser.username)) return false;
        if (!this.getStatus().equals(localUser.getStatus())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
