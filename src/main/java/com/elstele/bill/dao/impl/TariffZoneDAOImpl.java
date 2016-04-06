package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.TariffZoneDAO;
import com.elstele.bill.domain.TariffZone;
import com.elstele.bill.utils.Enums.Status;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public TariffZone getUniqueTariffZoneByZoneID(int zoneId) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from TariffZone t where t.zoneId = :zoneId ")
                .setParameter("zoneId", zoneId)
                .setMaxResults(1);
        return (TariffZone) query.uniqueResult();
    }

    @Override
    public List<TariffZone> getTariffZoneList() {
        Query query = getSessionFactory().getCurrentSession().createQuery("from TariffZone t where t.status is null or t.status <> 'DELETED' order by t.zoneName");
        return (List<TariffZone>) query.list();
    }

    @Override
    public List<TariffZone> getOnlyActualTariffZoneList() {
        DetachedCriteria maxQuery = DetachedCriteria.forClass(TariffZone.class);
        maxQuery.setProjection(Projections.max("validFrom"));

        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(TariffZone.class);
        criteria.add(Restrictions.disjunction()
                    .add(Restrictions.isNull("status"))
                    .add(Restrictions.ne("status", Status.DELETED)))
                    .add(Property.forName("validFrom").eq(maxQuery))
                    .addOrder(Order.asc("zoneName"));
        return (List<TariffZone>) criteria.list();
    }

    @Override
    public TariffZone getZoneByNameAndValidFrom(String zoneName, Date validFrom) {
        Query query = getSessionFactory().getCurrentSession().createQuery("FROM TariffZone t where t.zoneName = :zoneName AND t.validFrom = :validFrom")
                .setParameter("zoneName", zoneName)
                .setParameter("validFrom", validFrom);
        return (TariffZone) query.uniqueResult();
    }

    @Override
    public Integer setValidToDateForZones(Date newDateFromFile, Date validTo){
        Query query = getSessionFactory().getCurrentSession().createQuery("update TariffZone set validTo = :validTo where (validFrom is null or validFrom < :newDateFromFile) " +
                "AND (validTo is null or validTo > :newDateFromFile)")
                .setParameter("validTo", validTo)
                .setParameter("newDateFromFile", newDateFromFile);
        return query.executeUpdate();
    }

    @Override
    public List<TariffZone> getTariffZoneByValidFromDate(Date validFrom) {
        Query query = getSessionFactory().getCurrentSession().createQuery("FROM TariffZone where validFrom = :validFrom")
                .setParameter("validFrom", validFrom);
        return (List<TariffZone>)query.list();
    }
}
