package com.elstele.bill.assembler;

import com.elstele.bill.domain.ExternalPaymentTransaction;
import com.elstele.bill.form.ExternalPaymentForm;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * Created by ivan on 15/12/27.
 */
public class ExternalPaymentAssembler {
    public ExternalPaymentForm fromBeanToForm(ExternalPaymentTransaction bean){
        ExternalPaymentForm form = new ExternalPaymentForm();
        copyProperties(bean, form);
        return form;
    }

    public ExternalPaymentTransaction formFormToBean(ExternalPaymentForm form){
        ExternalPaymentTransaction bean = new ExternalPaymentTransaction();
        copyProperties(form, bean);
        return bean;
    }
}
