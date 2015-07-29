package com.elstele.bill.form;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;

/**
 * Created by X240 on 03/06/2015.
 */
public class LocalUserForm {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String password;
    private ArrayList<Integer> roleId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
