package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;

import javax.persistence.*;


@Entity
@Table(name="Devices")
public class Device extends CommonDomainBean {
    private String name;
    private String description;
    private String community;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ipAddress_id")
    private Ip ipAdd;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="deviceType_id")
    private DeviceTypes deviceTypes;

    public DeviceTypes getDeviceTypes() {
        return deviceTypes;
    }

    public void setDeviceTypes(DeviceTypes deviceTypes) {
        this.deviceTypes = deviceTypes;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public Ip getIpAdd() {
        return ipAdd;
    }

    public void setIpAdd(Ip ipAdd) {
        this.ipAdd = ipAdd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Device device = (Device) o;

        if (name != null ? !name.equals(device.name) : device.name != null) return false;
        if (description != null ? !description.equals(device.description) : device.description != null) return false;
        if (community != null ? !community.equals(device.community) : device.community != null) return false;
        if (ipAdd != null ? !ipAdd.equals(device.ipAdd) : device.ipAdd != null) return false;
        return !(deviceTypes != null ? !deviceTypes.equals(device.deviceTypes) : device.deviceTypes != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (community != null ? community.hashCode() : 0);
        result = 31 * result + (ipAdd != null ? ipAdd.hashCode() : 0);
        result = 31 * result + (deviceTypes != null ? deviceTypes.hashCode() : 0);
        return result;
    }
}
