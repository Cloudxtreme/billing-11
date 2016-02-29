package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.TariffZoneDAO;
import com.elstele.bill.domain.TariffZone;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TariffZoneDAOImpl extends CommonDAOImpl<TariffZone> implements TariffZoneDAO {
    @Override
    public List<TariffZone> getTariffZoneByZoneID(int zoneId) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from TariffZone t where t.zoneId = :zoneId ")
                .setParameter("zoneId", zoneId);
        return (List<TariffZone>) query.list();
    }

    @Override
    public List<TariffZone> getTariffZoneList() {
        Query query = getSessionFactory().getCurrentSession().createQuery("from TariffZone t where t.status is null or t.status <> 'DELETED' order by t.zoneName");
        return (List<TariffZone>) query.list();
    }

    @Override
    public List<Integer> getPrefProfileIdList() {
        Query query = getSessionFactory().getCurrentSession().createSQLQuery("SELECT DISTINCT profile_id FROM preference_rules ORDER BY profile_id");
        return query.list();
    }
}
