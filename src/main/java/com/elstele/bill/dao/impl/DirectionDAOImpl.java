package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.DirectionDAO;
import com.elstele.bill.domain.Direction;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.Status;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DirectionDAOImpl extends CommonDAOImpl<Direction> implements DirectionDAO {

    @Override
    public List<Direction> getDirectionList(int offset, int rows, String prefix) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Direction.class);
        criteria.add(Restrictions.disjunction()
                .add(Restrictions.isNull("status"))
                .add(Restrictions.ne("status", Status.DELETED)))
                .add(Restrictions.disjunction()
                        .add(Restrictions.like("prefix", prefix, MatchMode.START))
                        .add(Restrictions.like("prefix", "0" + prefix, MatchMode.START))
                        .add(Restrictions.like("prefix", "00" + prefix, MatchMode.START)))
                .addOrder(Order.desc("validFrom"))
                .addOrder(Order.asc("description"))
                .addOrder(Order.asc("prefix"))
                .setMaxResults(rows)
                .setFirstResult(offset);

        return (List<Direction>) criteria.list();
    }

    @Override
    public int getPagesCount(String prefix) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Direction.class);
        criteria.add(Restrictions.disjunction()
                .add(Restrictions.isNull("status"))
                .add(Restrictions.ne("status", Status.DELETED)))
                .add(Restrictions.disjunction()
                        .add(Restrictions.like("prefix", prefix, MatchMode.START))
                        .add(Restrictions.like("prefix", "0" + prefix, MatchMode.START))
                        .add(Restrictions.like("prefix", "00" + prefix, MatchMode.START)))
                .setProjection(Projections.rowCount());
        Long res = (Long) criteria.uniqueResult();
        return res.intValue();
    }

    @Override
    public Direction getByPrefixMainPart(String prefixPart) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Direction.class);
        criteria.add(Restrictions.like("prefix", "00" + prefixPart, MatchMode.START)).setMaxResults(1);
        return (Direction) criteria.uniqueResult();
    }

    @Override
    public Direction getDirectionByPrefixAndDate(String prefix, Date validateFrom) {
        Query query = getSessionFactory().getCurrentSession().createQuery("FROM Direction where prefix = :prefix AND validFrom = :validFrom")
                .setParameter("prefix", prefix)
                .setParameter("validFrom", validateFrom);
        return (Direction) query.uniqueResult();
    }

    @Override
    public Direction getDirectionForDateCorrecting(String prefix, Date validateFromNew, String getType) {
        Query query = getSessionFactory().getCurrentSession().createQuery("FROM Direction where prefix = :prefix " +
                "AND validFrom " + getSign(getType) + " :validateFromNew AND (status <> 'DELETED' or status is null) order by validFrom "+getOrderType(getType))
                .setParameter("prefix", prefix)
                .setParameter("validateFromNew", validateFromNew);
        List<Direction> directionList = query.list();
        if (directionList.isEmpty()) {
            return null;
        }
        return directionList.get(0);
    }

    @Override
    public Integer setValidToDateForDirections(Date newDateFromFile, Date validTo) {
        Query query = getSessionFactory().getCurrentSession().createQuery("update Direction set validTo = :validTo where (validFrom is null or validFrom < :newDateFromFile) " +
                "AND (validTo is null or validTo > :newDateFromFile) AND prefix like '00%'")
                .setParameter("validTo", validTo)
                .setParameter("newDateFromFile", newDateFromFile);
        return query.executeUpdate();
    }

    @Override
    public List<Direction> getDirectionListByValidFromDate(Date validFrom) {
        Query query = getSessionFactory().getCurrentSession().createQuery("From Direction  where validFrom = :validFrom")
                .setParameter("validFrom", validFrom);
        return (List<Direction>)query.list();
    }

    @Override
    public Direction getDirectionWithLatestDate(Date validFrom) {
        Query query = getSessionFactory().getCurrentSession().createQuery("FROM Direction where validFrom > :validFrom order BY validFrom ASC")
                .setParameter("validFrom", validFrom)
                .setMaxResults(1);
        return (Direction)query.uniqueResult();
    }

    private String getSign(String getType) {
        switch (getType) {
            case Constants.BIGGER:
                return ">";
            case Constants.SMALLER:
                return "<";
            default:
                return "=";
        }
    }

    private String getOrderType(String getType) {
        switch (getType) {
            case Constants.BIGGER:
                return "ASC";
            case Constants.SMALLER:
                return "DESC";
            default:
                return "DESC";
        }
    }


}
