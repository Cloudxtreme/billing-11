package com.elstele.bill.assembler;

import com.elstele.bill.domain.ServiceInternet;
import com.elstele.bill.domain.ServiceInternetAttribute;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.form.ServiceInternetAttributeForm;
import com.elstele.bill.form.ServiceTypeForm;
import com.elstele.bill.utils.Enums.Status;

import static org.springframework.beans.BeanUtils.copyProperties;

public class ServiceTypeAssembler {

    String[] propsToSkipInternetSrvAttribure = {"serviceInternet"};

    public ServiceTypeForm fromBeanToForm(ServiceType bean){
        ServiceTypeForm form = new ServiceTypeForm();
        copyProperties(bean,form);
        return form;
    }
    public ServiceType fromFormToBean(ServiceTypeForm form){
        ServiceType bean = new ServiceType();
        copyProperties(form, bean);
        if(form.getId() == null){
            bean.setStatus(Status.ACTIVE);
        }
        return bean;
    }

    public ServiceInternetAttributeForm fromServiceInternetAttributeBeanToForm(ServiceInternetAttribute bean){
        ServiceInternetAttributeForm form = new ServiceInternetAttributeForm();
        copyProperties(bean, form, propsToSkipInternetSrvAttribure);
        form.setServiceTypeId(bean.getServiceType().getId());
        return form;
    }

    public ServiceInternetAttribute fromServiceInternetAttributeFormToBean(ServiceInternetAttributeForm form){
        ServiceInternetAttribute bean = new ServiceInternetAttribute();
        copyProperties(form, bean, propsToSkipInternetSrvAttribure);

        ServiceType serviceType = new ServiceType();
        serviceType.setId(form.getServiceTypeId());
        bean.setServiceType(serviceType);

        return bean;
    }

}
