package com.elstele.bill.test.builder.bean;

import com.elstele.bill.domain.Activity;
import com.elstele.bill.utils.Enums.Status;
import org.apache.commons.lang.RandomStringUtils;

public class ActivityBuilder {
    private Activity activity;

    public ActivityBuilder build(){
        activity = new Activity();
        activity.setStatus(Status.ACTIVE);
        return this;
    }
    public Activity getRes(){
        if(activity == null){
            build();
        }
        return activity;
    }

    public ActivityBuilder randomActivity(){
        activity.setName(RandomStringUtils.randomAlphanumeric(4));
        activity.setDescription(RandomStringUtils.randomAlphanumeric(10));
        return this;
    }

    public ActivityBuilder withId(Integer id){
        activity.setId(id);
        return this;
    }

    public ActivityBuilder withName(String name){
        activity.setName(name);
        return this;
    }

    public ActivityBuilder withDescription(String description){
        activity.setDescription(description);
        return this;
    }
}
