package com.elstele.bill.domain;


import javax.persistence.*;

@Entity
@Table(name="LocalUsers")
public class LocalUser {

    @Id
    @Column(name="id")
    @GeneratedValue
    private Integer id;
    private String username;
    private String password;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

        if (!id.equals(localUser.id)) return false;
        if (!password.equals(localUser.password)) return false;
        if (!username.equals(localUser.username)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
