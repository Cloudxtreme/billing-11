package com.elstele.bill.assembler;
import com.elstele.bill.domain.ServiceInternet;
import com.elstele.bill.domain.ServicePhone;
import com.elstele.bill.form.*;
import com.elstele.bill.utils.Enums.Status;

import static org.springframework.beans.BeanUtils.copyProperties;

public class ServiceAssembler{

    String[] propsToSkip = {"serviceInternet", "servicePhone","serviceType"};

    public ServiceForm fromInternetBeanToForm(ServiceInternet bean){
        ServiceForm form = new ServiceForm();
        copyProperties(bean, form, propsToSkip);
        form.setAccountId(bean.getAccount().getId());
        form.setServiceInternet(serviceInternetFromBeanToForm((ServiceInternet) bean, form.getServiceInternet()));

        ServiceTypeAssembler ServiceTypeAssembler = new ServiceTypeAssembler();
        form.setServiceType(ServiceTypeAssembler.fromBeanToForm(bean.getServiceType()));
        return form;
    }

    private ServiceInternetForm serviceInternetFromBeanToForm(ServiceInternet bean, ServiceInternetForm form) {
        if (bean != null){
            if (form == null){
                form = new ServiceInternetForm();
            }
            copyProperties(bean, form);
        }
        return form;
    }

    public ServiceForm fromPhoneBeanToForm(ServicePhone bean){
        ServiceForm form = new ServiceForm();
        copyProperties(bean, form, propsToSkip);
        form.setAccountId(bean.getAccount().getId());
        form.setServicePhone(servicePhoneFromBeanToForm((ServicePhone) bean, form.getServicePhone()));

        ServiceTypeAssembler ServiceTypeAssembler = new ServiceTypeAssembler();
        form.setServiceType(ServiceTypeAssembler.fromBeanToForm(bean.getServiceType()));
        return form;
    }
    private ServicePhoneForm servicePhoneFromBeanToForm(ServicePhone bean, ServicePhoneForm form) {
        if (bean != null){
            if (form == null){
                form = new ServicePhoneForm();
            }
            copyProperties(bean, form);
        }
        return form;
    }

    public ServiceInternet fromFormToInternetBean(ServiceForm form){
        ServiceInternet bean = new ServiceInternet();
        copyProperties(form, bean, propsToSkip);
        if (form.getServiceInternet() != null){
                bean.setStatus(Status.ACTIVE);
                copyProperties(form.getServiceInternet(), bean);
        }
        return bean;
    }

    public ServicePhone fromFormToPhoneBean(ServiceForm form){
        ServicePhone bean = new ServicePhone();
        copyProperties(form, bean, propsToSkip);
        if (form.getServicePhone() != null){
            bean.setStatus(Status.ACTIVE);
            copyProperties(form.getServicePhone(), bean);
        }
        return bean;
    }
}
