package com.elstele.bill.test.builder.form;

import com.elstele.bill.form.DeviceForm;
import com.elstele.bill.form.IpForm;
import com.elstele.bill.form.ServiceForm;
import com.elstele.bill.form.ServiceTypeForm;
import com.elstele.bill.utils.Constants;

import java.util.Date;

public class ServiceInternetFormBuilder {
    private ServiceForm serviceForm;

    public ServiceInternetFormBuilder build() {
        serviceForm = new ServiceForm();
        return this;
    }

    public ServiceForm getRes() {
        if (serviceForm == null){
            build();
        }
        return serviceForm;
    }

    public ServiceInternetFormBuilder withId(Integer id){
        serviceForm.setId(id);
        return this;
    }

    public ServiceInternetFormBuilder withPeriod(Constants.Period period){
        serviceForm.setPeriod(period);
        return this;
    }

    public ServiceInternetFormBuilder withDateStart(Date date){
        serviceForm.setDateStart(date);
        return this;
    }

    public ServiceInternetFormBuilder withUsername(String username){
        serviceForm.getServiceInternet().setUsername(username);
        return this;
    }

    public ServiceInternetFormBuilder withPassword(String password){
        serviceForm.getServiceInternet().setPassword(password);
        return this;
    }

    public ServiceInternetFormBuilder withAccount(Integer accountId){
        serviceForm.setAccountId(accountId);
        return this;
    }

    public ServiceInternetFormBuilder withServiceType(ServiceTypeForm serviceTypeForm){
        serviceForm.setServiceType(serviceTypeForm);
        return this;
    }
}
