package com.elstele.bill.test.builder;

import com.elstele.bill.domain.ServiceInternetAttribute;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.utils.Enums.Status;
import org.apache.commons.lang.RandomStringUtils;

public class ServiceAttributeBuilder {
    private ServiceInternetAttribute serviceInternetAttribute;

    public ServiceAttributeBuilder build() {
        serviceInternetAttribute = new ServiceInternetAttribute();
        serviceInternetAttribute.setStatus(Status.ACTIVE);
        return this;
    }
    public ServiceInternetAttribute getRes(){
        if (serviceInternetAttribute == null){
            build();
        }
        return serviceInternetAttribute;
    }

    public ServiceAttributeBuilder randomServiceAttribute(){
        serviceInternetAttribute.setAttribute(RandomStringUtils.randomAlphanumeric(4));
        serviceInternetAttribute.setOperation("=");
        serviceInternetAttribute.setValue(RandomStringUtils.randomAlphanumeric(4));
        return this;
    }
    public ServiceAttributeBuilder withAttribute(String attribute){
        serviceInternetAttribute.setAttribute(attribute);
        return this;
    }
    public ServiceAttributeBuilder withOperation(String operation){
        serviceInternetAttribute.setOperation(operation);
        return this;
    }
    public ServiceAttributeBuilder withValue(String value){
        serviceInternetAttribute.setValue(value);
        return this;
    }
    public ServiceAttributeBuilder withServiceType(ServiceType serviceType){
        serviceInternetAttribute.setServiceType(serviceType);
        return this;
    }
}
