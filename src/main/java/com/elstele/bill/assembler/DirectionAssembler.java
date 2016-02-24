package com.elstele.bill.assembler;

import com.elstele.bill.dao.interfaces.TariffZoneDAO;
import com.elstele.bill.domain.Direction;
import com.elstele.bill.domain.TariffZone;
import com.elstele.bill.form.DirectionForm;
import com.elstele.bill.form.TariffZoneForm;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

public class DirectionAssembler {
    private TariffZoneAssembler tariffZoneAssembler = new TariffZoneAssembler();
    private TariffZoneDAO tariffZoneDAO;

    public DirectionAssembler(TariffZoneDAO tariffZoneDAO) {
        this.tariffZoneDAO = tariffZoneDAO;
    }

    public DirectionForm fromBeanToForm(Direction bean){
        DirectionForm form = new DirectionForm();
        copyProperties(bean, form);
        if(bean.getTarifZone() != null) {
            List<TariffZone> tariffZoneList = tariffZoneDAO.getTariffZoneByZoneID(bean.getTarifZone());
            List<TariffZoneForm> tariffZoneFormList = new ArrayList<>();
            for(TariffZone tariffZone : tariffZoneList){
                tariffZoneFormList.add(tariffZoneAssembler.fromBeanToForm(tariffZone));
            }
            form.setTariffZoneList(tariffZoneFormList);
        }
        return form;
    }

    public Direction fromFormToBean(DirectionForm form){
        Direction bean = new Direction();
        copyProperties(form, bean);

        return bean;
    }
}
