package com.elstele.bill.test.builder.form;

import com.elstele.bill.form.TariffZoneForm;

import java.util.Date;

public class TariffZoneFormBuilder {
    private TariffZoneForm tariffZone;

    public TariffZoneFormBuilder build() {
        tariffZone = new TariffZoneForm();
        return this;
    }

    public TariffZoneFormBuilder withId(int id) {
        tariffZone.setId(id);
        return this;
    }

    public TariffZoneFormBuilder withZoneId(int zoneId) {
        tariffZone.setZoneId(zoneId);
        return this;
    }

    public TariffZoneFormBuilder withValidTo(Long validTo) {
        tariffZone.setValidTo(validTo);
        return this;
    }

    public TariffZoneFormBuilder withValidFrom(Long validFrom) {
        tariffZone.setValidFrom(validFrom);
        return this;
    }

    public TariffZoneFormBuilder withZoneName(String zoneName) {
        tariffZone.setZoneName(zoneName);
        return this;
    }

    public TariffZoneFormBuilder withAdditionalKode(String additionalKode) {
        tariffZone.setAdditionalKode(additionalKode);
        return this;
    }

    public TariffZoneFormBuilder withDollar(boolean dollar) {
        tariffZone.setDollar(dollar);
        return this;
    }

    public TariffZoneFormBuilder withPrefProfileId(int prefProfileId) {
        tariffZone.setPrefProfile(prefProfileId);
        return this;
    }

    public TariffZoneFormBuilder withTariff(float tariff) {
        tariffZone.setTarif(tariff);
        return this;
    }

    public TariffZoneFormBuilder withTarifPref(float tarifPref) {
        tariffZone.setTarifPref(tarifPref);
        return this;
    }

    public TariffZoneFormBuilder withValidFromAsDate(Date validFromAsDate){
        tariffZone.setValidFromAsDate(validFromAsDate);
        return this;
    }

    public TariffZoneFormBuilder withValidToAsDate(Date validToAsDate){
        tariffZone.setValidToAsDate(validToAsDate);
        return this;
    }

    public TariffZoneForm getRes() {
        if (tariffZone == null) {
            build();
        }
        return tariffZone;
    }
}
