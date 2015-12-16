package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;
import com.elstele.bill.utils.Constants;
import org.hibernate.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Service")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name="discriminator",
        discriminatorType=DiscriminatorType.STRING
)
@DiscriminatorValue(value="MAIN")
public class Service extends CommonDomainBean{
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dateStart;

    private Constants.Period period;

    @ManyToOne(fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name="account_id")
    private Account account;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ServiceType serviceType;

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Constants.Period getPeriod() {
        return period;
    }

    public void setPeriod(Constants.Period period) {
        this.period = period;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Service service = (Service) o;

        //TODO change this evaluation
        if (account != null  &&
                (account.getId() != null ? !account.getId().equals(service.account.getId()) : service.account.getId() != null)) {
            return false;
        }
        if (dateStart != null ? !dateStart.equals(service.dateStart) : service.dateStart != null) return false;
        if (period != service.period) return false;
        if (serviceType != null ? !serviceType.equals(service.serviceType) : service.serviceType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dateStart != null ? dateStart.hashCode() : 0;
        result = 31 * result + (period != null ? period.hashCode() : 0);
        result = 31 * result + (account.getId() != null ? account.getId().hashCode() : 0);
        result = 31 * result + (serviceType != null ? serviceType.hashCode() : 0);
        return result;
    }
}
