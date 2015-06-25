package com.elstele.bill.form;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 24.06.15
 * Time: 17:31
 * To change this template use File | Settings | File Templates.
 */
public class UserRoleForm {
    private String roleName;
    private ArrayList<ActivityForm> activityList;

    public void setRoleName(String roleName){
        this.roleName = roleName;
    }

    public String getRoleName(){
        return roleName;
    }

    public void setActivityList(ArrayList<ActivityForm> activity){
        this.activityList = activity;
    }

    public ArrayList<ActivityForm> getActivityList(){
        return activityList;
    }
}
