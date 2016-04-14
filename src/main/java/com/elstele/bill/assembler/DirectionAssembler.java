package com.elstele.bill.assembler;

import com.elstele.bill.dao.interfaces.TariffZoneDAO;
import com.elstele.bill.domain.Direction;
import com.elstele.bill.domain.TariffZone;
import com.elstele.bill.form.DirectionForm;
import com.elstele.bill.form.TariffZoneForm;
import com.elstele.bill.reportCreators.dateparser.DateReportParser;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

public class DirectionAssembler {
    private TariffZoneAssembler tariffZoneAssembler = new TariffZoneAssembler();
    private TariffZoneDAO tariffZoneDAO;
    private final String[] propToSkip = new String[]{"validFrom", "validTo"};

    public DirectionAssembler(TariffZoneDAO tariffZoneDAO) {
        this.tariffZoneDAO = tariffZoneDAO;
    }

    public DirectionForm fromBeanToForm(Direction bean){
        DirectionForm form = new DirectionForm();
        copyProperties(bean, form, propToSkip);
        if(bean.getTarifZone() != null) {
            List<TariffZone> tariffZoneList = tariffZoneDAO.getTariffZoneByZoneID(bean.getTarifZone());
            List<TariffZoneForm> tariffZoneFormList = new ArrayList<>();
            for(TariffZone tariffZone : tariffZoneList){
                tariffZoneFormList.add(tariffZoneAssembler.fromBeanToForm(tariffZone));
            }
            form.setTariffZoneList(tariffZoneFormList);
        }

        form.setValidFrom((bean.getValidFrom() != null ? bean.getValidFrom().getTime() : 0));
        form.setValidTo((bean.getValidTo() != null ? bean.getValidTo().getTime() : 0));
        return form;
    }

    public Direction fromFormToBean(DirectionForm form){
        Direction bean = new Direction();
        int beanZoneId = form.getZoneId();
        TariffZone tariffZone = tariffZoneDAO.getById(beanZoneId);
        copyProperties(form, bean, propToSkip);
        if(form.getValidFrom() != 0){
            bean.setValidFrom(DateReportParser.getOnlyDateFromLongValue(form.getValidFrom()));
        }
        if(form.getValidTo() != 0){
            bean.setValidTo(DateReportParser.getOnlyDateFromLongValue(form.getValidTo()));
        }
        bean.setTarifZone(tariffZone.getZoneId());
        return bean;
    }
}
