package com.elstele.bill.test.builder.bean;

import com.elstele.bill.domain.Address;
import com.elstele.bill.domain.Device;
import com.elstele.bill.domain.DeviceTypes;
import com.elstele.bill.domain.Ip;
import com.elstele.bill.utils.Enums.Status;

public class DeviceBuilder implements TestObjectCreator<DeviceBuilder, Device> {
    private Device device;

    @Override
    public DeviceBuilder build() {
        device = new Device();
        device.setStatus(Status.ACTIVE);
        return this;
    }

    public DeviceBuilder withId(Integer id){
        device.setId(id);
        return this;
    }

    public DeviceBuilder withName(String name){
        device.setName(name);
        return this;
    }

    public DeviceBuilder withDescription(String description){
        device.setDescription(description);
        return this;
    }

    public DeviceBuilder withCommunity(String community){
        device.setCommunity(community);
        return this;
    }

    public DeviceBuilder withIpAdd(Ip ip){
        device.setIpAdd(ip);
        return this;
    }

    public DeviceBuilder withDeviceType(DeviceTypes deviceType){
        device.setDeviceType(deviceType);
        return this;
    }

    public DeviceBuilder withAddress(Address address){
        device.setDeviceAddress(address);
        return this;
    }


    @Override
    public Device getRes() {
        if(device == null){
            this.build();
        }
        return device;
    }
}
