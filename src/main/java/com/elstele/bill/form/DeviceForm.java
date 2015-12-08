package com.elstele.bill.form;

import com.elstele.bill.utils.Enums.Status;

public class DeviceForm {

    private String name;
    private Integer id;
    private Status status;
    private DeviceTypesForm devType;
    private String description;
    private String community;
    private IpForm ipForm;
    private AddressForm deviceAddressForm;

    public DeviceTypesForm getDevType() {
        return devType;
    }

    public void setDevType(DeviceTypesForm devType) {
        this.devType = devType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public IpForm getIpForm() {
        return ipForm;
    }

    public void setIpForm(IpForm ipForm) {
        this.ipForm = ipForm;
    }

    public AddressForm getDeviceAddressForm() {
        return deviceAddressForm;
    }

    public void setDeviceAddressForm(AddressForm deviceAddressForm) {
        this.deviceAddressForm = deviceAddressForm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceForm that = (DeviceForm) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (status != that.status) return false;
        if (devType != null ? !devType.equals(that.devType) : that.devType != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (community != null ? !community.equals(that.community) : that.community != null) return false;
        return !(ipForm != null ? !ipForm.equals(that.ipForm) : that.ipForm != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (devType != null ? devType.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (community != null ? community.hashCode() : 0);
        result = 31 * result + (ipForm != null ? ipForm.hashCode() : 0);
        return result;
    }
}

