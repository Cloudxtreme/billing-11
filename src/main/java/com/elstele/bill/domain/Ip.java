package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;
import com.elstele.bill.utils.IpStatus;

import javax.persistence.*;
import java.util.Set;

    @Entity
    @Table(name="ipAddress")
    public class Ip extends CommonDomainBean{

        public String ipName;

    @Enumerated(EnumType.STRING)
    public IpStatus ipStatus;

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

        public IpStatus getIpStatus() {
            return ipStatus;
        }

        public void setIpStatus(IpStatus ipStatus) {
            this.ipStatus = ipStatus;
        }
    }

