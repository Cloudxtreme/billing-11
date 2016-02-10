package com.elstele.bill.assembler;

import com.elstele.bill.domain.AuditedObject;
import com.elstele.bill.form.AuditedObjectForm;

import static org.springframework.beans.BeanUtils.copyProperties;

public class AuditedObjectAssembler {
    public AuditedObjectForm fromBeanToForm(AuditedObject bean){
        AuditedObjectForm form = new AuditedObjectForm();
        copyProperties(bean,form);
        return form;
    }

    public AuditedObject fromFormToBean(AuditedObjectForm form){
        AuditedObject bean = new AuditedObject();
        copyProperties(form, bean);
        return bean;
    }
}
