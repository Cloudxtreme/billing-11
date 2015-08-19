package com.elstele.bill.assembler;

import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.form.LocalUserForm;

import static org.springframework.beans.BeanUtils.copyProperties;

public class LocalUserAssembler {
    public LocalUserForm fromBeanToForm(LocalUser bean){
        LocalUserForm form = new LocalUserForm();
        copyProperties(bean,form);
        return form;
    }

    public LocalUser fromFormToBean(LocalUserForm form){
        LocalUser bean = new LocalUser();
        copyProperties(form, bean);
        return bean;
    }

}
