package com.elstele.bill.form;

import javax.validation.constraints.NotNull;

import java.util.ArrayList;

public class LocalUserForm {
    private Integer id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String passwordConfirm;
    private ArrayList<Integer> roleId;


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

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public void setRoleId(ArrayList<Integer> role){
        this.roleId = role;
    }

    public ArrayList<Integer> getRoleId(){
        return roleId;
    }

    //Check if this is for New of Update
    public boolean isNew() {
        return (this.id == null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocalUserForm that = (LocalUserForm) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (passwordConfirm != null ? !passwordConfirm.equals(that.passwordConfirm) : that.passwordConfirm != null)
            return false;
        return !(roleId != null ? !roleId.equals(that.roleId) : that.roleId != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (passwordConfirm != null ? passwordConfirm.hashCode() : 0);
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        return result;
    }
}
