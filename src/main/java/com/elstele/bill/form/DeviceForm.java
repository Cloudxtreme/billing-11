package com.elstele.bill.form;

import com.elstele.bill.domain.DeviceTypes;
import com.elstele.bill.utils.Status;

public class DeviceForm {

    private String name;

    public DeviceTypesForm getDevType() {
        return devType;
    }

    public void setDevType(DeviceTypesForm devType) {
        this.devType = devType;
    }

    private Integer id;
    private Status status;
    private DeviceTypesForm devType;
    private String description;
    private String community;
    private String ip;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
