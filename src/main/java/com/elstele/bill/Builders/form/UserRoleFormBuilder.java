package com.elstele.bill.Builders.form;

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
    public UserRoleFormBuilder withActivityId(List<Integer> acList){
        userRoleForm.setActivityId(acList);
        return this;
    }
    public UserRoleFormBuilder withActivityName(List<String> acList){
        userRoleForm.setActivityName(acList);
        return this;
    }
}
