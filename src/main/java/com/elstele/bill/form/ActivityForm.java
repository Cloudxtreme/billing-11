package com.elstele.bill.form;

import com.sun.istack.internal.NotNull;

public class ActivityForm {
    // form:hidden - hidden value
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

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

    //Check if this is for New of Update
    public boolean isNew() {
        return (this.id == null);
    }

}
