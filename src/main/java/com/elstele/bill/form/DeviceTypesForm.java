package com.elstele.bill.form;


import com.elstele.bill.utils.Status;

public class DeviceTypesForm {
    public Integer id;
    public String deviceType;
    public Status status;
    public String description;
    public Integer portsNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
