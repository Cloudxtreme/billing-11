package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;
import com.elstele.bill.utils.Enums.IpStatus;

import javax.persistence.*;

@Entity
@Table(name = "ipAddress")
public class Ip extends CommonDomainBean {

    public String ipName;

    @Enumerated(EnumType.STRING)
    public IpStatus ipStatus;

    @ManyToOne
    @JoinColumn(name = "subnet_id")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ip ip = (Ip) o;

        if (ipName != null ? !ipName.equals(ip.ipName) : ip.ipName != null) return false;
        if (ipStatus != ip.ipStatus) return false;
        return !(ipSubnet != null ? !ipSubnet.equals(ip.ipSubnet) : ip.ipSubnet != null);

    }

    @Override
    public int hashCode() {
        int result = ipName != null ? ipName.hashCode() : 0;
        result = 31 * result + (ipStatus != null ? ipStatus.hashCode() : 0);
        result = 31 * result + (ipSubnet != null ? ipSubnet.hashCode() : 0);
        return result;
    }
}

