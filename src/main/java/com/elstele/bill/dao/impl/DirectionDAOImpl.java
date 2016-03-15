package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.DirectionDAO;
import com.elstele.bill.domain.Direction;
import com.elstele.bill.utils.Enums.Status;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectionDAOImpl extends CommonDAOImpl<Direction> implements DirectionDAO {

    @Override
    public List<Direction> getDirectionList(int offset, int rows, String prefix) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Direction.class);
        criteria.add(Restrictions.eq("status", null))
                .add(Restrictions.ne("status", Status.DELETED))
                .add(Restrictions.like("prefix", prefix, MatchMode.END))
                .add(Restrictions.like("prefix", "0" + prefix, MatchMode.END))
                .add(Restrictions.like("prefix", "00" + prefix, MatchMode.END))
                .addOrder(Order.asc("description"));

        return (List<Direction>) criteria.list();
    }

    @Override
    public int getPagesCount(String prefix) {
        Query q = getSessionFactory().getCurrentSession().
                createQuery("select count(* ) from Direction where prefix like '" + prefix + "%'");
        Long res = (Long) q.uniqueResult();
        return res.intValue();
    }

    @Override
    public Direction getByPrefix(String prefix) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from Direction where prefix= :prefix")
                .setParameter("prefix", prefix);
        return (Direction) query.uniqueResult();
    }
}
