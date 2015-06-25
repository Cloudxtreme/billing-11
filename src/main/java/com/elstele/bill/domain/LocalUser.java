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
//    @ManyToOne
//    private UserRoleForm userrole;



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

/*
    public UserRoleForm getUserrole() {
        return userrole;
    }

    public void setUserrole(UserRoleForm userrole) {
        this.userrole = userrole;
    }
*/

}
