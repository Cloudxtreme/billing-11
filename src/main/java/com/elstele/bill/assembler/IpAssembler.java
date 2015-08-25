package com.elstele.bill.assembler;

import com.elstele.bill.domain.Ip;
import com.elstele.bill.domain.IpSubnet;
import com.elstele.bill.form.IpForm;
import com.elstele.bill.form.IpSubnetForm;

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

    public IpSubnetForm fromBeanToFormIpSubnet(IpSubnet bean){
        IpSubnetForm form = new IpSubnetForm();
        copyProperties(bean, form);
        return  form;
    }

    public IpSubnet fromFormToBeanIpSubnet(IpSubnetForm form){
        IpSubnet bean = new IpSubnet();
        copyProperties(form, bean);
        return bean;
    }
}
