package com.elstele.bill.form;

import javax.validation.constraints.NotNull;

import java.util.List;

public class UserRoleForm {

    // form:hidden - hidden value
    private Integer id;

    @NotNull
    private String name;
    @NotNull
    private String description;
    private List<Integer> activityId;


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

    public void setActivityId(List<Integer> activity){
        this.activityId = activity;
    }

    public List<Integer> getActivityId(){
        return activityId;
    }

    //Check if this is for New of Update
    public boolean isNew() {
        return (this.id == null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRoleForm that = (UserRoleForm) o;

        if (activityId != null ? !activityId.equals(that.activityId) : that.activityId != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + (activityId != null ? activityId.hashCode() : 0);
        return result;
    }
}
