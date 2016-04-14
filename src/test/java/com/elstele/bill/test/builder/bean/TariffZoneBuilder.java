package com.elstele.bill.test.builder.bean;

import com.elstele.bill.domain.TariffZone;
import com.elstele.bill.utils.Enums.Status;

import java.util.Date;

public class TariffZoneBuilder implements TestObjectCreator<TariffZoneBuilder, TariffZone> {
    private TariffZone tariffZone;

    @Override
    public TariffZoneBuilder build() {
        tariffZone = new TariffZone();
        tariffZone.setStatus(Status.ACTIVE);
        return this;
    }

    public TariffZoneBuilder withId(int id) {
        tariffZone.setId(id);
        return this;
    }

    public TariffZoneBuilder withZoneId(int zoneId) {
        tariffZone.setZoneId(zoneId);
        return this;
    }

    public TariffZoneBuilder withValidTo(Date validTo) {
        tariffZone.setValidTo(validTo);
        return this;
    }

    public TariffZoneBuilder withValidFrom(Date validFrom) {
        tariffZone.setValidFrom(validFrom);
        return this;
    }

    public TariffZoneBuilder withZoneName(String zoneName) {
        tariffZone.setZoneName(zoneName);
        return this;
    }

    public TariffZoneBuilder withAdditionalKode(String additionalKode) {
        tariffZone.setAdditionalKode(additionalKode);
        return this;
    }

    public TariffZoneBuilder withDollar(boolean dollar) {
        tariffZone.setDollar(dollar);
        return this;
    }

    public TariffZoneBuilder withPrefProfileId(int prefProfileId) {
        tariffZone.setPrefProfile(prefProfileId);
        return this;
    }

    public TariffZoneBuilder withTariff(float tariff) {
        tariffZone.setTarif(tariff);
        return this;
    }

    public TariffZoneBuilder withTarifPref(float tarifPref) {
        tariffZone.setTarifPref(tarifPref);
        return this;
    }

    @Override
    public TariffZone getRes() {
        if (tariffZone == null) {
            build();
        }
        return tariffZone;
    }
}
