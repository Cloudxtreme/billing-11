package com.elstele.bill.form;

import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.domain.ServiceT;

import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Date;

public class ServiceUserForm {
    private Integer id;
    private Integer userId;
    private Integer serviceId;
    private String dateStart;
    private String dateEnd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    //Check if this is for New of Update
    public boolean isNew() {
        return (this.id == null);
    }
}
