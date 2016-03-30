package com.elstele.bill.assembler;

import com.elstele.bill.domain.TariffZone;
import com.elstele.bill.form.TariffZoneForm;
import com.elstele.bill.reportCreators.dateparser.DateReportParser;

import static org.springframework.beans.BeanUtils.copyProperties;

public class TariffZoneAssembler {
    private final String[] propToSkip = new String[]{"validFrom", "validTo"};

    public TariffZoneForm fromBeanToForm(TariffZone bean){
        TariffZoneForm form = new TariffZoneForm();
        copyProperties(bean,form, propToSkip);
        form.setValidFrom((bean.getValidFrom() != null ? bean.getValidFrom().getTime() : 0));
        form.setValidTo((bean.getValidTo() != null ? bean.getValidTo().getTime() : 0));
        return form;
    }

    public TariffZone fromFormToBean(TariffZoneForm form){
        TariffZone bean = new TariffZone();
        copyProperties(form, bean, propToSkip);
        if(form.getValidFrom() != 0){
            bean.setValidFrom(DateReportParser.getOnlyDateFromLongValue(form.getValidFrom()));
        }
        if(form.getValidTo() != 0){
            bean.setValidTo(DateReportParser.getOnlyDateFromLongValue(form.getValidTo()));
        }
        return bean;
    }
}
