package com.elstele.bill.dao.interfaces;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.TariffZone;

import java.util.List;

public interface TariffZoneDAO extends CommonDAO<TariffZone> {
    List<TariffZone> getTariffZoneByZoneID(int zoneId);
}
