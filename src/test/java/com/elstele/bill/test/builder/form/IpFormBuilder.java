package com.elstele.bill.test.builder.form;

import com.elstele.bill.domain.IpSubnet;
import com.elstele.bill.form.IpForm;
import com.elstele.bill.utils.Enums.Status;

public class IpFormBuilder {
    private IpForm form;

    public IpFormBuilder build(){
        form = new IpForm();
        form.setStatus(Status.ACTIVE);
        return this;
    }

    public IpFormBuilder withId(Integer value){
        form.setId(value);
        return this;
    }

    public IpFormBuilder withIpName(String value){
        form.setIpName(value);
        return this;
    }

    public IpFormBuilder withIpSubnet(IpSubnet value){
        form.setIpSubnet(value);
        return this;
    }

    public IpForm getRes(){
        if(form == null){
            build();
        }
        return form;
    }
}
