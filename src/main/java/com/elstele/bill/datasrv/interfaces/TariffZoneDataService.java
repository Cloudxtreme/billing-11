package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.domain.TariffZone;
import com.elstele.bill.form.TariffZoneForm;

import java.util.List;

public interface TariffZoneDataService {
    public List<TariffZoneForm> getTariffZonesList();
    public void create(TariffZoneForm tariffZoneForm);
    public String deleteZone(int id);
    public TariffZoneForm getZoneById(int id);
    public void updateZone(TariffZoneForm tariffZoneForm);
    public void changeSoftBlockStatus(int zoneId);
    public List<Integer> getPrefProfileIdList();
    public TariffZone getUniqueZoneByZoneId(int zoneId);
}
