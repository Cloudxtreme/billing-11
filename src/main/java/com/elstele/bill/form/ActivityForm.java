package com.elstele.bill.form;

public class ActivityForm {
    private String name;
    private String description;
    private Integer activityId;

    public void setName(String activity){
        this.name = activity;
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

    public void setActivityId(Integer activityId){
        this.activityId = activityId;
    }

    public Integer getActivityId(){
        return activityId;
    }
}
