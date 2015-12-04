package com.elstele.bill.form;


import com.elstele.bill.utils.Enums.SubnetPurpose;

public class IpSubnetForm {
    Integer id;
    String ipSubnet;
    SubnetPurpose subnetPurpose;

    public String getIpSubnet() {
        return ipSubnet;
    }

    public void setIpSubnet(String ipSubnet) {
        this.ipSubnet = ipSubnet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (o == null || getClass() != o.getClass()) return false;

        IpSubnetForm that = (IpSubnetForm) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (ipSubnet != null ? !ipSubnet.equals(that.ipSubnet) : that.ipSubnet != null) return false;
        return subnetPurpose == that.subnetPurpose;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (ipSubnet != null ? ipSubnet.hashCode() : 0);
        result = 31 * result + (subnetPurpose != null ? subnetPurpose.hashCode() : 0);
        return result;
    }
}
