package com.elstele.bill.Builders.form;

import com.elstele.bill.form.ServiceForm;
import com.elstele.bill.form.ServiceTypeForm;
import com.elstele.bill.utils.Constants;

import java.util.Date;

public class ServicePhoneFormBuilder{
    private ServiceForm serviceForm;

    public ServicePhoneFormBuilder build() {
        serviceForm = new ServiceForm();
        return this;
    }

    public ServiceForm getRes() {
        if (serviceForm == null){
            build();
        }
        return serviceForm;
    }

    public ServicePhoneFormBuilder withId(Integer id){
        serviceForm.setId(id);
        return this;
    }
    public ServicePhoneFormBuilder withDateStart(Date date){
        serviceForm.setDateStart(date);
        return this;
    }
    public ServicePhoneFormBuilder withPeriod(Constants.Period period){
        serviceForm.setPeriod(period);
        return this;
    }
    public ServicePhoneFormBuilder withPhoneNumber(String phone){
        serviceForm.getServicePhone().setPhoneNumber(phone);
        return this;
    }
    public ServicePhoneFormBuilder withAccount(Integer accountId){
        serviceForm.setAccountId(accountId);
        return this;
    }
    public ServicePhoneFormBuilder withServiceType(ServiceTypeForm serviceTypeForm){
        serviceForm.setServiceType(serviceTypeForm);
        return this;
    }
}
