package com.elstele.bill.form;





import com.sun.istack.internal.NotNull;

import java.util.ArrayList;

public class UserRoleForm {
    @NotNull
    private String name;
    @NotNull
    private String description;
    private ArrayList<Integer> activityId;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setActivityId(ArrayList<Integer> activity){
        this.activityId = activity;
    }

    public ArrayList<Integer> getActivityId(){
        return activityId;
    }
}
