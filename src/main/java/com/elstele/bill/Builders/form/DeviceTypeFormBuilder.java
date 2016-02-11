package com.elstele.bill.Builders.form;

import com.elstele.bill.form.DeviceTypesForm;
import com.elstele.bill.utils.Enums.Status;

public class DeviceTypeFormBuilder {
    private DeviceTypesForm form;

    public DeviceTypeFormBuilder build(){
        form = new DeviceTypesForm();
        form.setStatus(Status.ACTIVE);
        return this;
    }

    public DeviceTypeFormBuilder withId(Integer value){
        form.setId(value);
        return this;
    }

    public DeviceTypeFormBuilder withDeviceType(String value){
        form.setDeviceType(value);
        return this;
    }

    public DeviceTypeFormBuilder withDescription(String value){
        form.setDescription(value);
        return this;
    }

    public DeviceTypeFormBuilder withPortsNumber(Integer value){
        form.setPortsNumber(value);
        return this;
    }


    public DeviceTypesForm getRes(){
        if(form == null){
            build();
        }
        return form;
    }
}
