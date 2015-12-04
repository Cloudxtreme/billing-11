package com.elstele.bill.test.builder.form;

import com.elstele.bill.form.ActivityForm;

public class ActivityFormBuilder {
    private ActivityForm activityForm;

    public ActivityFormBuilder build(){
        activityForm = new ActivityForm();
        return this;
    }
    public ActivityForm getRes(){
        if(activityForm == null){
            build();
        }
        return activityForm;
    }

    public ActivityFormBuilder withId(Integer id){
        activityForm.setId(id);
        return this;
    }

    public ActivityFormBuilder withName(String name){
        activityForm.setName(name);
        return this;
    }

    public ActivityFormBuilder withDescription(String description){
        activityForm.setDescription(description);
        return this;
    }
}
