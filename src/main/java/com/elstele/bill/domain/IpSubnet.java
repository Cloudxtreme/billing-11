package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;
import com.elstele.bill.utils.Enums.SubnetPurpose;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="ipsubnet")
public class IpSubnet extends CommonDomainBean {
    public String ipSubnet;

    @Enumerated(EnumType.STRING)
    public SubnetPurpose subnetPurpose;

    @OneToMany(mappedBy="ipSubnet")
    private Set<Ip> ipAdd;

    public String getIpSubnet() {
        return ipSubnet;
    }

    public void setIpSubnet(String ipSubnet) {
        this.ipSubnet = ipSubnet;
    }

    public Set<Ip> getIpAdd() {
        return ipAdd;
    }

    public void setIpAdd(Set<Ip> ipAdd) {
        this.ipAdd = ipAdd;
    }

    public SubnetPurpose getSubnetPurpose() {
        return subnetPurpose;
    }

    public void setSubnetPurpose(SubnetPurpose subnetPurpose) {
        this.subnetPurpose = subnetPurpose;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IpSubnet)) return false;
        IpSubnet that = (IpSubnet) o;
        return Objects.equals(ipSubnet, that.ipSubnet) &&
                Objects.equals(subnetPurpose, that.subnetPurpose) &&
                Objects.equals(ipAdd, that.ipAdd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipSubnet, subnetPurpose, ipAdd);
    }
}
