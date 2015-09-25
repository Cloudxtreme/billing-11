package com.elstele.bill.form;

import com.elstele.bill.domain.IpSubnet;
import com.elstele.bill.utils.IpStatus;
import com.elstele.bill.utils.Status;

public class IpForm {
    public Integer id;
    public String ipName;
    public IpSubnet ipSubnet;
    public Status status;
    public IpStatus ipStatus;


    public IpStatus getIpStatus() {
        return ipStatus;
    }

    public void setIpStatus(IpStatus ipStatus) {
        this.ipStatus = ipStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
