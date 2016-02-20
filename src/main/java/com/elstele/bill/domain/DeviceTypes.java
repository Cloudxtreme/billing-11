package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="DeviceTypes")
public class DeviceTypes extends CommonDomainBean{

    public String deviceType;
    public String description;
    public Integer portsNumber;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPortsNumber() {
        return portsNumber;
    }

    public void setPortsNumber(Integer portsNumber) {
        this.portsNumber = portsNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceTypes that = (DeviceTypes) o;

        if (deviceType != null ? !deviceType.equals(that.deviceType) : that.deviceType != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return !(portsNumber != null ? !portsNumber.equals(that.portsNumber) : that.portsNumber != null);

    }

    @Override
    public int hashCode() {
        int result = deviceType != null ? deviceType.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (portsNumber != null ? portsNumber.hashCode() : 0);
        return result;
    }
}
