package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@FilterDef(name="showActive", parameters={
        @ParamDef( name="exclude", type="string" )
})
@Table(name="UserService")
public class UserService extends CommonDomainBean{
    private Date dateStart;
    private Date dateEnd;
    @OneToOne
    @JoinColumn(name = "localUser_id")
    private LocalUser user;
    @OneToOne
    @JoinColumn(name = "service_id")
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
