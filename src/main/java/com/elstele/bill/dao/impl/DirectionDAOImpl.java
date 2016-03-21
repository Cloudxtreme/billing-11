package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.DirectionDAO;
import com.elstele.bill.domain.Direction;
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
                .addOrder(Order.asc("description"))
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
    public Direction getByPrefix(String prefix) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from Direction where prefix= :prefix")
                .setParameter("prefix", prefix);
        return (Direction) query.uniqueResult();
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


}
