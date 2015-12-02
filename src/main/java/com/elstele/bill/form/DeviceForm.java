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
}

