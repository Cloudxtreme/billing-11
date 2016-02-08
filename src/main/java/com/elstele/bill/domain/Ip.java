package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;
import com.elstele.bill.utils.Enums.IpStatus;

import javax.persistence.*;
import java.util.Objects;

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
        if (!(o instanceof Ip)) return false;
        Ip that = (Ip) o;
        return Objects.equals(ipName, that.ipName) &&
                Objects.equals(ipSubnet, that.ipSubnet) &&
                Objects.equals(ipStatus, that.ipStatus);
    }


    @Override
    public int hashCode() {
        return Objects.hash(ipName, ipStatus, ipSubnet);
    }
}

