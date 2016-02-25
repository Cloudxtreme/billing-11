package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.form.TariffZoneForm;

import java.util.List;

public interface TariffZoneDataService {
    List<TariffZoneForm> getTariffZonesList();
    void create(TariffZoneForm tariffZoneForm);
    String deleteZone(int id);
    TariffZoneForm getZoneById(int id);
    void updateZone(TariffZoneForm tariffZoneForm);
    void changeSoftBlockStatus(int zoneId);
}
