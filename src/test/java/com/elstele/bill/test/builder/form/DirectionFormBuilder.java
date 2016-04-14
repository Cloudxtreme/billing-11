package com.elstele.bill.test.builder.form;


import com.elstele.bill.form.DirectionForm;
import com.elstele.bill.form.TariffZoneForm;

import java.util.List;

public class DirectionFormBuilder {
    private DirectionForm form;

    public DirectionFormBuilder build() {
        form = new DirectionForm();
        return this;
    }

    public DirectionFormBuilder withId(int id){
        form.setId(id);
        return this;
    }

    public DirectionFormBuilder withDescription(String description){
        form.setDescription(description);
        return this;
    }
    public DirectionFormBuilder withPrefix(String prefix){
        form.setPrefix(prefix);
        return this;
    }
    public DirectionFormBuilder withTariffZoneList(List<TariffZoneForm> tariffZone){
        form.setTariffZoneList(tariffZone);
        return this;
    }
    public DirectionFormBuilder withAdditionalKode(String additionalKode){
        form.setAdditionalKode(additionalKode);
        return this;
    }
    public DirectionFormBuilder withTrunkGroup(String trunkGroup){
        form.setTrunkgroup(trunkGroup);
        return this;
    }
    public DirectionFormBuilder withZoneId(int zoneId){
        form.setZoneId(zoneId);
        return this;
    }
    public DirectionFormBuilder withValidFromDate(Long validFrom){
        form.setValidFrom(validFrom);
        return this;
    }
    public DirectionFormBuilder withValidToDate(Long validTo){
        form.setValidTo(validTo);
        return this;
    }

    public DirectionForm getRes() {
        if(form == null){
            build();
        }
        return form;
    }
}

