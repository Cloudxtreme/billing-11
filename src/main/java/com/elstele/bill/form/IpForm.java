package com.elstele.bill.form;

import com.elstele.bill.domain.IpSubnet;
import com.elstele.bill.utils.Enums.IpStatus;
import com.elstele.bill.utils.Enums.Status;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IpForm ipForm = (IpForm) o;

        if (id != null ? !id.equals(ipForm.id) : ipForm.id != null) return false;
        if (ipName != null ? !ipName.equals(ipForm.ipName) : ipForm.ipName != null) return false;
        if (ipSubnet != null ? !ipSubnet.equals(ipForm.ipSubnet) : ipForm.ipSubnet != null) return false;
        if (status != null && ipForm.status != null) return status == ipForm.status;
        return ipStatus == ipForm.ipStatus;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (ipName != null ? ipName.hashCode() : 0);
        result = 31 * result + (ipSubnet != null ? ipSubnet.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (ipStatus != null ? ipStatus.hashCode() : 0);
        return result;
    }
}
