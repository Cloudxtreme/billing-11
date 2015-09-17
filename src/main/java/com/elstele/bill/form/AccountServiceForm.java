package com.elstele.bill.form;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Date;

public class AccountServiceForm {
    private Integer id;
//    private Integer accountId;
    private AccountForm account = new AccountForm();
    private ServiceForm service = new ServiceForm();
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dateStart;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dateEnd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AccountForm getAccount() {
        return account;
    }

    public void setAccount(AccountForm account) {
        this.account = account;
    }

    public ServiceForm getService() {
        return service;
    }

    public void setService(ServiceForm service) {
        this.service = service;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    //Check if this is for New of Update
    public boolean isNew() {
        return (this.id == null);
    }
}
