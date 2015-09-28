package com.elstele.bill.form;


import com.elstele.bill.utils.IpStatus;
import com.elstele.bill.utils.SubnetPurpose;

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
}
