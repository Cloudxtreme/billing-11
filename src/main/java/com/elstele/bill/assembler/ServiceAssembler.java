package com.elstele.bill.assembler;

import com.elstele.bill.domain.ServiceInternet;
import com.elstele.bill.domain.ServicePhone;
import com.elstele.bill.domain.ServiceT;
import com.elstele.bill.form.ServiceForm;

import static org.springframework.beans.BeanUtils.copyProperties;

public class ServiceAssembler {
    public ServiceForm fromBeanToForm(ServiceT bean){
        ServiceForm form = new ServiceForm();
        copyProperties(bean,form);
        return form;
    }


    public ServiceInternet fromFormToInternetBean(ServiceForm form){
        ServiceInternet bean = new ServiceInternet();
        String exclude[] = new String[] {"phoneNumber"};
        copyProperties(form, bean, exclude);
        return bean;
    }
    public ServicePhone fromFormToPhoneBean(ServiceForm form){
        ServicePhone bean = new ServicePhone();
        String exclude[] = new String[] {"ip"};
        copyProperties(form, bean, exclude);
        return bean;
    }
}
