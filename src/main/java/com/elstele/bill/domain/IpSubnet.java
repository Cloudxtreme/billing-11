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

        IpSubnet ipSubnet1 = (IpSubnet) o;

        if (ipAdd != null ? !ipAdd.equals(ipSubnet1.ipAdd) : ipSubnet1.ipAdd != null) return false;
        if (ipSubnet != null ? !ipSubnet.equals(ipSubnet1.ipSubnet) : ipSubnet1.ipSubnet != null) return false;
        if (subnetPurpose != ipSubnet1.subnetPurpose) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ipSubnet != null ? ipSubnet.hashCode() : 0;
        result = 31 * result + (subnetPurpose != null ? subnetPurpose.hashCode() : 0);
        result = 31 * result + (ipAdd != null ? ipAdd.hashCode() : 0);
        return result;
    }
}
