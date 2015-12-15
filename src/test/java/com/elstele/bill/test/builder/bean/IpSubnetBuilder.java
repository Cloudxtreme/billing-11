package com.elstele.bill.test.builder.bean;

import com.elstele.bill.domain.IpSubnet;
import com.elstele.bill.utils.Enums.Status;
import com.elstele.bill.utils.Enums.SubnetPurpose;

public class IpSubnetBuilder implements TestObjectCreator<IpSubnetBuilder, IpSubnet> {
    private IpSubnet ipSubnet;

    @Override
    public IpSubnetBuilder build() {
        ipSubnet = new IpSubnet();
        ipSubnet.setStatus(Status.ACTIVE);
        return this;
    }

    public IpSubnetBuilder withIpSubnet(String name){
        ipSubnet.setIpSubnet(name);
        return this;
    }

    public IpSubnetBuilder withSubnetPurpose(SubnetPurpose purpose){
        ipSubnet.setSubnetPurpose(purpose);
        return this;
    }

    @Override
    public IpSubnet getRes() {
        if(ipSubnet==null){
            build();
        }
        return ipSubnet;
    }
}
