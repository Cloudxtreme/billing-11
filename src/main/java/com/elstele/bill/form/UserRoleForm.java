package com.elstele.bill.form;

import javax.validation.constraints.NotNull;

import java.util.ArrayList;

public class UserRoleForm {

    // form:hidden - hidden value
    private Integer id;

    @NotNull
    private String name;
    @NotNull
    private String description;
    private ArrayList<Integer> activityId;


    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

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

    //Check if this is for New of Update
    public boolean isNew() {
        return (this.id == null);
    }
}
