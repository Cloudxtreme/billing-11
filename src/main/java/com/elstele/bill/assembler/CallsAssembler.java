package com.elstele.bill.assembler;

import com.elstele.bill.domain.Calls;
import com.elstele.bill.form.CallsForm;
import static org.springframework.beans.BeanUtils.copyProperties;

public class CallsAssembler {

    public CallsForm fromBeanToForm(Calls bean){

        CallsForm form = new CallsForm();
        copyProperties(bean,form);
        return form;
    }

    public Calls fromFormToBean(CallsForm form){
        Calls bean = new Calls();
        copyProperties(form, bean);
        return bean;
    }
}
