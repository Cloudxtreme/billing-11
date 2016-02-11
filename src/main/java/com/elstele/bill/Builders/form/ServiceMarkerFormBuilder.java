package com.elstele.bill.Builders.form;

import com.elstele.bill.form.ServiceForm;
import com.elstele.bill.form.ServiceTypeForm;
import com.elstele.bill.utils.Constants;

import java.util.Date;

public class ServiceMarkerFormBuilder{
    private ServiceForm serviceForm;

    public ServiceMarkerFormBuilder build() {
        serviceForm = new ServiceForm();
        return this;
    }

    public ServiceForm getRes() {
        if (serviceForm == null){
            build();
        }
        return serviceForm;
    }

    public ServiceMarkerFormBuilder withId(Integer id){
        serviceForm.setId(id);
        return this;
    }

    public ServiceMarkerFormBuilder withPeriod(Constants.Period period){
        serviceForm.setPeriod(period);
        return this;
    }

    public ServiceMarkerFormBuilder withDateStart(Date date){
        serviceForm.setDateStart(date);
        return this;
    }

    public ServiceMarkerFormBuilder withAccount(Integer accountId){
        serviceForm.setAccountId(accountId);
        return this;
    }

    public ServiceMarkerFormBuilder withServiceType(ServiceTypeForm serviceTypeForm){
        serviceForm.setServiceType(serviceTypeForm);
        return this;
    }
}
