package com.elstele.bill.form;

import java.util.ArrayList;

/**
 * Created by X240 on 03/06/2015.
 */
public class LocalUserForm {
    private Integer id;
    private String userName;
    private String userPass;
//    private ArrayList<UserRoleForm> roleList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

/*
    public ArrayList<UserRoleForm> getRoleList(){
        return roleList;
    }

    public void setRoleList(ArrayList<UserRoleForm> roleList){
        this.roleList = roleList;
    }
*/
}
