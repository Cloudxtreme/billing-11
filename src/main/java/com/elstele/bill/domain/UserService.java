package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="UserService")
public class UserService extends CommonDomainBean{
    private Date dateStart;
    private Date dateEnd;

    @ManyToOne(fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name="user_id")
    private LocalUser user;

    @OneToOne(cascade = CascadeType.ALL)
    private ServiceT service;

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

    public LocalUser getUser() {
        return user;
    }

    public void setUser(LocalUser user) {
        this.user = user;
    }

    public ServiceT getService() {
        return service;
    }

    public void setService(ServiceT service) {
        this.service = service;
    }
}
