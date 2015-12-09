package com.elstele.bill.test.builder.form;

import com.elstele.bill.domain.ServiceInternetAttribute;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.form.ServiceTypeForm;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.Status;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ServiceTypeFormBuilder {
    private ServiceTypeForm serviceTypeForm;

    public ServiceTypeFormBuilder build() {
        serviceTypeForm = new ServiceTypeForm();
        return this;
    }

    public ServiceTypeForm getRes(){
        if (serviceTypeForm == null){
            build();
        }
        return serviceTypeForm;
    }

    public ServiceTypeFormBuilder withId(Integer id){
        serviceTypeForm.setId(id);
        return this;
    }

    public ServiceTypeFormBuilder withName(String name){
        serviceTypeForm.setName(name);
        return this;
    }

    public ServiceTypeFormBuilder withPrice(Float price){
        serviceTypeForm.setPrice(price);
        return this;
    }

    public ServiceTypeFormBuilder withDescription(String description){
        serviceTypeForm.setDescription(description);
        return this;
    }

    public ServiceTypeFormBuilder withServiceType(String type){
        serviceTypeForm.setServiceType(type);
        return this;
    }

    public ServiceTypeFormBuilder withBussType(Constants.AccountType bussType){
        serviceTypeForm.setBussType(bussType);
        return this;
    }
}
