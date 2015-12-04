package com.elstele.bill.test.builder.bean;

import com.elstele.bill.domain.Activity;
import com.elstele.bill.domain.UserRole;
import com.elstele.bill.utils.Enums.Status;
import org.apache.commons.lang.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class UserRoleBuilder {
    private UserRole userRole;

    public UserRoleBuilder build(){
        userRole = new UserRole();
        userRole.setStatus(Status.ACTIVE);
        return this;
    }

    public UserRole getRes(){
        if(userRole==null)
            build();
        return userRole;
    }

    public UserRoleBuilder randomUserRole(){
        userRole.setName(RandomStringUtils.randomAlphanumeric(4));
        userRole.setDescription(RandomStringUtils.randomAlphanumeric(4));

        ActivityBuilder ab = new ActivityBuilder();
        Activity ac1 = ab.build().randomActivity().getRes();
        Activity ac2 = ab.build().randomActivity().getRes();
        List<Activity> acList = new ArrayList<Activity>();
        acList.add(ac1);
        acList.add(ac2);
        userRole.setActivities(acList);
        return this;
    }

    public UserRoleBuilder withId(Integer id){
        userRole.setId(id);
        return this;
    }
    public UserRoleBuilder withName(String name){
        userRole.setName(name);
        return this;
    }
    public UserRoleBuilder withDescription(String description){
        userRole.setDescription(description);
        return this;
    }
    public UserRoleBuilder withActivities(List<Activity> acList){
        userRole.setActivities(acList);
        return this;
    }
}
