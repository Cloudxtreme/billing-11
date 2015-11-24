package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;
import com.elstele.bill.utils.Enums.IpStatus;

import javax.persistence.*;

@Entity
    @Table(name="ipAddress")
    public class Ip extends CommonDomainBean{

        public String ipName;

    @Enumerated(EnumType.STRING)
    public IpStatus ipStatus;

    @ManyToOne
    @JoinColumn(name="subnet_id")
    private IpSubnet ipSubnet;



        public String getIpName() {
            return ipName;
        }

        public void setIpName(String ipName) {
            this.ipName = ipName;
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

