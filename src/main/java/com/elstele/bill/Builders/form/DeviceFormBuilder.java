package com.elstele.bill.Builders.form;

import com.elstele.bill.form.AddressForm;
import com.elstele.bill.form.DeviceForm;
import com.elstele.bill.form.DeviceTypesForm;
import com.elstele.bill.form.IpForm;
import com.elstele.bill.utils.Enums.Status;

public class DeviceFormBuilder {
    private DeviceForm form;

    public DeviceFormBuilder build(){
        form = new DeviceForm();
        form.setStatus(Status.ACTIVE);
        return this;
    }

    public DeviceFormBuilder withId(Integer value){
        form.setId(value);
        return this;
    }

    public DeviceFormBuilder withName(String value){
        form.setName(value);
        return this;
    }

    public DeviceFormBuilder withDeviceTypeForm(DeviceTypesForm value){
        form.setDevType(value);
        return this;
    }

    public DeviceFormBuilder withDescription(String value){
        form.setDescription(value);
        return this;
    }


    public DeviceFormBuilder withCommunity(String value){
        form.setCommunity(value);
        return this;
    }

    public DeviceFormBuilder withIpForm(IpForm value){
        form.setIpForm(value);
        return this;
    }


    public DeviceFormBuilder withAddressForm(AddressForm value){
        form.setDeviceAddressForm(value);
        return this;
    }

    public DeviceForm getRes(){
        if(form == null){
            build();
        }
        return form;
    }
}
