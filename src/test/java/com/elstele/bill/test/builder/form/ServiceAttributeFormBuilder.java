package com.elstele.bill.test.builder.form;

import com.elstele.bill.form.ServiceInternetAttributeForm;

public class ServiceAttributeFormBuilder {
    private ServiceInternetAttributeForm serviceInternetAttributeForm;

    public ServiceAttributeFormBuilder build() {
        serviceInternetAttributeForm = new ServiceInternetAttributeForm();
        return this;
    }
    public ServiceInternetAttributeForm getRes(){
        if (serviceInternetAttributeForm == null){
            build();
        }
        return serviceInternetAttributeForm;
    }

    public ServiceAttributeFormBuilder withId(Integer id){
        serviceInternetAttributeForm.setId(id);
        return this;
    }
    public ServiceAttributeFormBuilder withAttribute(String attribute){
        serviceInternetAttributeForm.setAttribute(attribute);
        return this;
    }
    public ServiceAttributeFormBuilder withOperation(String operation){
        serviceInternetAttributeForm.setOperation(operation);
        return this;
    }
    public ServiceAttributeFormBuilder withValue(String value){
        serviceInternetAttributeForm.setValue(value);
        return this;
    }
    public ServiceAttributeFormBuilder withServiceType(Integer id){
        serviceInternetAttributeForm.setServiceTypeId(id);
        return this;
    }
}
