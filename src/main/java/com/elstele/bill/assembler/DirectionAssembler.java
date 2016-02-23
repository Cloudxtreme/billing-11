package com.elstele.bill.assembler;

import com.elstele.bill.domain.Direction;
import com.elstele.bill.form.DirectionForm;
import static org.springframework.beans.BeanUtils.copyProperties;

public class DirectionAssembler {
    private TariffZoneAssembler tariffZoneAssembler = new TariffZoneAssembler();
    String[] propsToSkip = {"tariffZone"};

    public DirectionForm fromBeanToForm(Direction bean){
        DirectionForm form = new DirectionForm();
        copyProperties(bean, form, propsToSkip);
        if(bean.getTariffZone() != null)
        form.setTariffZone(tariffZoneAssembler.fromBeanToForm(bean.getTariffZone()));
        return form;
    }

    public Direction fromFormToBean(DirectionForm form){
        Direction bean = new Direction();
        copyProperties(form, bean, propsToSkip);
        bean.setTariffZone(tariffZoneAssembler.fromFormToBean(form.getTariffZone()));
        return bean;
    }
}
