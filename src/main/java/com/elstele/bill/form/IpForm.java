package com.elstele.bill.form;

import com.elstele.bill.domain.IpSubnet;
import com.elstele.bill.utils.Enums.IpStatus;
import com.elstele.bill.utils.Enums.Status;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        return Objects.hash(id, ipName, ipSubnet,status, ipStatus);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IpForm)) return false;
        IpForm that = (IpForm) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(ipName, that.ipName) &&
                Objects.equals(ipSubnet, that.ipSubnet)&&
                Objects.equals(status, that.status)&&
                Objects.equals(ipStatus, that.ipStatus);
    }

}
