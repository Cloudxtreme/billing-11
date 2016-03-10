package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.DirectionDAO;
import com.elstele.bill.domain.Direction;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectionDAOImpl extends CommonDAOImpl<Direction> implements DirectionDAO {

    @Override
    public List<Direction> getDirectionList(int offset, int rows, String prefix) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from Direction d where (d.status is null or d.status <> 'DELETED') and d.prefix like '%" + prefix+ "%' order by d.description");
        query.setFirstResult(offset).setMaxResults(rows);
        return (List<Direction>)query.list();
    }

    @Override
    public int getPagesCount(String prefix) {
        Query q = getSessionFactory().getCurrentSession().
                createQuery("select count(* ) from Direction where prefix like '%" + prefix+ "%'");
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
