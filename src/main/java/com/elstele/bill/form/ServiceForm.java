package com.elstele.bill.form;

import com.elstele.bill.utils.Constants;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

import java.util.Date;

public class ServiceForm {
    private Integer id;
    private Integer accountId;
    private ServiceInternetForm serviceInternet = new ServiceInternetForm();
    private ServicePhoneForm servicePhone = new ServicePhoneForm();
    private ServiceTypeForm serviceType = new ServiceTypeForm();


    @NotNull
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dateStart;

    private Constants.Period period;

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

    public ServiceInternetForm getServiceInternet() {
        return serviceInternet;
    }

    public void setServiceInternet(ServiceInternetForm serviceInternet) {
        this.serviceInternet = serviceInternet;
    }

    public ServicePhoneForm getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(ServicePhoneForm servicePhone) {
        this.servicePhone = servicePhone;
    }

    public ServiceTypeForm getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceTypeForm serviceType) {
        this.serviceType = serviceType;
    }

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

    //Check if this is for New of Update
    public boolean isNew() {
        return (this.id == null);
    }
}
