package com.elstele.bill.domain;


import com.elstele.bill.domain.common.CommonDomainBean;
import com.elstele.bill.usersDataStorage.UserStateStorage;
import com.elstele.bill.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@FilterDef(name="showActive", parameters={
        @ParamDef( name="exclude", type="string" )
})
@Table(name="LocalUsers")
public class LocalUser extends CommonDomainBean implements Serializable, HttpSessionBindingListener {
//    private static final long serialVersionUID = 1L;

    @Column(unique = true)
    private String username;
    private String password;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "USER_USERROLE",
            joinColumns = @JoinColumn(name = "LOCALUSER_ID", unique=false),
            inverseJoinColumns = @JoinColumn(name = "USERROLE_ID", unique=false))
    @Filter(name="showActive", condition="status != :exclude")
    private List<UserRole> userRoles = new ArrayList<UserRole>();

    final static Logger LOGGER = LogManager.getLogger(LocalUser.class);

    public LocalUser(){
        super();
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

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public void addUserRole(UserRole role){
        userRoles.add(role);
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

    @Override
    public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent) {
        LOGGER.info("User " + httpSessionBindingEvent.getSession().getId() + " logged in session");
        UserStateStorage.setElementToMap(httpSessionBindingEvent.getSession());
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent) {
        LOGGER.info("User " + httpSessionBindingEvent.getSession().getId() + " remove from session");
        UserStateStorage.removeElementFromMap(httpSessionBindingEvent.getSession());
    }

}
