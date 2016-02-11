package com.elstele.bill.Builders.bean;

import com.elstele.bill.domain.Ip;
import com.elstele.bill.domain.IpSubnet;
import com.elstele.bill.utils.Enums.IpStatus;

public class IpBuilder implements TestObjectCreator<IpBuilder, Ip> {
    private Ip ip;
    @Override
    public IpBuilder build() {
        ip = new Ip();
        ip.setIpStatus(IpStatus.FREE);
        return this;
    }

    public IpBuilder withId(Integer id){
        ip.setId(id);
        return this;
    }

    public IpBuilder withIpName(String name){
        ip.setIpName(name);
        return this;
    }

    public IpBuilder withIpSubnet(IpSubnet subnet){
        ip.setIpSubnet(subnet);
        return this;
    }

    @Override
    public Ip getRes() {
        if(ip == null){
            build();
        }
        return ip;
    }
}
