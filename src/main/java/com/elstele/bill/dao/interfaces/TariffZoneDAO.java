package com.elstele.bill.dao.interfaces;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.TariffZone;

import java.util.Date;
import java.util.List;

public interface TariffZoneDAO extends CommonDAO<TariffZone> {
    public List<TariffZone> getTariffZoneByZoneID(int zoneId);
    public TariffZone getUniqueTariffZoneByZoneID(int zoneId);
    public List<TariffZone> getTariffZoneList();
    public List<TariffZone> getOnlyActualTariffZoneList();
    public TariffZone getZoneByNameAndValidFrom(String zoneName, Date validFrom);
    public Integer setValidToDateForZones(Date newDateFromFile, Date validTo);
    public List<TariffZone> getTariffZoneByValidFromDate(Date validFrom);
}
