package com.elstele.bill.assembler;

import com.elstele.bill.domain.Street;
import com.elstele.bill.form.StreetForm;
import static org.springframework.beans.BeanUtils.copyProperties;

public class StreetAssembler {
    public StreetForm fromBeanToForm(Street bean){
        StreetForm form = new StreetForm();
        copyProperties(bean,form);
        return form;
    }

    public Street fromFormToBean(StreetForm form){
        Street bean = new Street();
        copyProperties(form,bean);
        return bean;
    }
}
