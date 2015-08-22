package com.elstele.bill.assembler;

import com.elstele.bill.domain.Ip;
import com.elstele.bill.form.IpForm;
import static org.springframework.beans.BeanUtils.copyProperties;

public class IpAssembler {
    public IpForm fromBeanToForm(Ip bean){
        IpForm form = new IpForm();

        copyProperties(bean,form);
        return form;
    }



    public Ip fromFormToBean(IpForm form){
        Ip bean = new Ip();
        copyProperties(form, bean);
        return bean;
    }
}
