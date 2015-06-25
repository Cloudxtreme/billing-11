package com.elstele.bill.form;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 24.06.15
 * Time: 17:22
 * To change this template use File | Settings | File Templates.
 */
public class ActivityForm {
    private String name;
    private String description;

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
}
