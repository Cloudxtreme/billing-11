package com.elstele.bill.test.builder.bean;

import com.elstele.bill.domain.DeviceTypes;
import com.elstele.bill.utils.Enums.Status;

public class DeviceTypeBuilder implements TestObjectCreator<DeviceTypeBuilder, DeviceTypes> {
    private DeviceTypes deviceType;

    @Override
    public DeviceTypeBuilder build() {
        deviceType = new DeviceTypes();
        deviceType.setStatus(Status.ACTIVE);
        return this;
    }

    public DeviceTypeBuilder withId(Integer id){
        deviceType.setId(id);
        return this;
    }

    public DeviceTypeBuilder withDeviceType(String name){
        deviceType.setDeviceType(name);
        return this;
    }

    public DeviceTypeBuilder withDescription(String description){
        deviceType.setDescription(description);
        return this;
    }

    public DeviceTypeBuilder withPortsNumber(Integer portsNumber){
        deviceType.setPortsNumber(portsNumber);
        return this;
    }

    @Override
    public DeviceTypes getRes() {
        if(deviceType == null){
            build();
        }
        return deviceType;
    }
}
