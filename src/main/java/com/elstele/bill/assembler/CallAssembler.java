package com.elstele.bill.assembler;

import com.elstele.bill.domain.Call;
import com.elstele.bill.form.CallForm;
import static org.springframework.beans.BeanUtils.copyProperties;

public class CallAssembler {

    public CallForm fromBeanToForm(Call bean){

        CallForm form = new CallForm();
        copyProperties(bean,form);
        return form;
    }

    public Call fromFormToBean(CallForm form){
        Call bean = new Call();
        copyProperties(form, bean);
        return bean;
    }
}
