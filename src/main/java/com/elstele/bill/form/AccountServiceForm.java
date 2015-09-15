package com.elstele.bill.form;

import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Date;

public class AccountServiceForm {
    private Integer id;
    private Integer accountId;
    private Integer serviceId;
    private String dateStart;
    private String dateEnd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
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
