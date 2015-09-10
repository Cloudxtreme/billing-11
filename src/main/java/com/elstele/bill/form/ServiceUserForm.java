package com.elstele.bill.form;

import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.domain.ServiceT;

import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Date;

public class ServiceUserForm {
    private Integer id;
    private LocalUser user;
    private ArrayList<ServiceT> service;
    private Date dateStart;
    private Date dateEnd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalUser getUser() {
        return user;
    }

    public void setUser(LocalUser user) {
        this.user = user;
    }

    public ArrayList<ServiceT> getService() {
        return service;
    }

    public void setService(ArrayList<ServiceT> service) {
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
