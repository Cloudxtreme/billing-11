package com.elstele.bill.Builders.form;

import com.elstele.bill.form.StreetForm;
import com.elstele.bill.utils.Enums.Status;

public class StreetFormBuilder {
    private StreetForm form;

    public StreetFormBuilder build(){
        form = new StreetForm();
        return this;
    }
    public StreetFormBuilder withName(String val){
        form.setName(val);
        return this;
    }
    public StreetFormBuilder withId(int val){
        form.setId(val);
        return this;
    }
    public StreetForm getRes(){
        if(form == null){
            build();
        }
        return form;
    }

}
