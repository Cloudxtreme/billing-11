package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.domain.TariffZone;
import com.elstele.bill.form.TariffZoneForm;

import java.util.Date;
import java.util.List;

public interface TariffZoneDataService {
    public List<TariffZoneForm> getTariffZonesList();
    public void create(TariffZoneForm tariffZoneForm);
    public int create(TariffZone tariffZone);
    public String deleteZone(int id);
    public TariffZoneForm getZoneById(int id);
    public void updateZone(TariffZoneForm tariffZoneForm);
    public List<Integer> getPrefProfileIdList();
    public TariffZone getUniqueZoneByZoneId(int zoneId);
    public TariffZone getZoneByNameAndValidFrom(String zoneName, Date validFrom);
    public Integer setValidToDateForZones(Date newDateFromFile);
    public List<TariffZoneForm> getOnlyActualTariffZoneList();
}
