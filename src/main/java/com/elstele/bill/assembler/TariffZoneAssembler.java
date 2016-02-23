package com.elstele.bill.assembler;

import com.elstele.bill.domain.TariffZone;
import com.elstele.bill.form.TariffZoneForm;

import static org.springframework.beans.BeanUtils.copyProperties;

public class TariffZoneAssembler {

    public TariffZoneForm fromBeanToForm(TariffZone bean){
        TariffZoneForm form = new TariffZoneForm();
        copyProperties(bean,form);
        return form;
    }

    public TariffZone fromFormToBean(TariffZoneForm form){
        TariffZone bean = new TariffZone();
        copyProperties(form, bean);
        return bean;
    }
}
