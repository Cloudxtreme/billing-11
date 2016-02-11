package com.elstele.bill.Builders.form;

import com.elstele.bill.form.IpSubnetForm;
import com.elstele.bill.utils.Enums.SubnetPurpose;

public class IpSubnetFormBuilder {
    private IpSubnetForm form;

    public IpSubnetFormBuilder build(){
        form = new IpSubnetForm();
        form.setSubnetPurpose(SubnetPurpose.MGMT);
        return this;
    }

    public IpSubnetFormBuilder withId(Integer id){
        form.setId(id);
        return this;
    }
    public IpSubnetFormBuilder withSubnet(String subnet){
        form.setIpSubnet(subnet);
        return this;
    }

    public IpSubnetForm getRes(){
        if(form == null){
            build();
        }
        return form;
    }
}
