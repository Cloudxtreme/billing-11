package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;

import javax.persistence.*;
import java.util.Set;

    @Entity
    @Table(name="ipAddress")
    public class Ip extends CommonDomainBean{

        public String ipName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="subnet_id")
    private IpSubnet ipSubnet;

    @OneToOne(mappedBy = "ipAdd")
    private Device device;

    public String getIpName() {
        return ipName;
    }

    public void setIpName(String ipName) {
        this.ipName = ipName;
    }


    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

        public IpSubnet getIpSubnet() {
            return ipSubnet;
        }

        public void setIpSubnet(IpSubnet ipSubnet) {
            this.ipSubnet = ipSubnet;
        }
    }

