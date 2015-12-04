package com.elstele.bill.test.builder.form;

import com.elstele.bill.form.UserRoleForm;

import java.util.List;

public class UserRoleFormBuilder {
    private UserRoleForm userRoleForm;

    public UserRoleFormBuilder build(){
        userRoleForm = new UserRoleForm();
        return this;
    }

    public UserRoleForm getRes(){
        if(userRoleForm==null)
            build();
        return userRoleForm;
    }

    public UserRoleFormBuilder withId(Integer id){
        userRoleForm.setId(id);
        return this;
    }
    public UserRoleFormBuilder withName(String name){
        userRoleForm.setName(name);
        return this;
    }
    public UserRoleFormBuilder withDescription(String description){
        userRoleForm.setDescription(description);
        return this;
    }
    public UserRoleFormBuilder withActivities(List<Integer> acList){
        userRoleForm.setActivityId(acList);
        return this;
    }
}
