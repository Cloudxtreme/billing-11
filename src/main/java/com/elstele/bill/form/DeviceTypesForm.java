package com.elstele.bill.form;


import com.elstele.bill.utils.Enums.Status;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceTypesForm that = (DeviceTypesForm) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (deviceType != null ? !deviceType.equals(that.deviceType) : that.deviceType != null) return false;
        if (status != null && that.status != null){
          return status==that.status;
        }
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return !(portsNumber != null ? !portsNumber.equals(that.portsNumber) : that.portsNumber != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (deviceType != null ? deviceType.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (portsNumber != null ? portsNumber.hashCode() : 0);
        return result;
    }
}
