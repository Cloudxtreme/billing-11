package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;


import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name="DeviceTypes")
public class DeviceTypes extends CommonDomainBean{

    public String deviceType;
    public String description;
    public Integer portsNumber;

    /*@OneToMany(mappedBy="deviceTypes")
    private Set<Device> devices;*/

  /*  public Set<Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }
*/
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
}
