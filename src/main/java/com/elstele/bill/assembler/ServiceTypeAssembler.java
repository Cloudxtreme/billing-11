package com.elstele.bill.assembler;

import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.form.ServiceTypeForm;

import static org.springframework.beans.BeanUtils.copyProperties;

public class ServiceTypeAssembler {

    public ServiceTypeForm fromBeanToForm(ServiceType bean){
        ServiceTypeForm form = new ServiceTypeForm();
        copyProperties(bean,form);
        return form;
    }
    public ServiceType fromFormToBean(ServiceTypeForm form){
        ServiceType bean = new ServiceType();
        copyProperties(form, bean);
        return bean;
    }
}
