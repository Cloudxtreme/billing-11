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
    public List<Direction> getDirectionList(int offset, int rows) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from Direction ");
        query.setFirstResult(offset).setMaxResults(rows);
        return (List<Direction>)query.list();
    }

    @Override
    public int getPagesCount() {
        Query q = getSessionFactory().getCurrentSession().
                createQuery("select count(* ) from Direction ");
        Long res = (Long) q.uniqueResult();
        return res.intValue();
    }

}
