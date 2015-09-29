package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.Call;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CallDAOImpl extends CommonDAOImpl<Call> implements CallDAO {

    @Override
    public List<Call> getCalls() {
        return null;
    }

    @Override
    public Integer getCallsCount() {
        Query q = getSessionFactory().getCurrentSession().
                createQuery("select count(* ) from Call");
        Long res = (Long) q.uniqueResult();
        return res.intValue();
    }

    @Override
    public List<Call> getCallsList(int limit, int offset) {
        Query q = getSessionFactory().getCurrentSession().
                createQuery("select a from Call a order by a.startTime");
        q.setFirstResult(offset).setMaxResults(limit);

        return (List<Call>) q.list();
    }

    public List<Call> callsListSelectionBySearch(int limit, int offset, String numberA, String numberB, Date startDate, Date endDate) {
        String queryStart = "select a from Call a where 1=1 ";
        String queryEnd = " order by a.startTime";
        if (numberA!=null && !numberA.isEmpty()) {
            numberA = "and numberA like '%" + numberA + "%'";
            queryStart += numberA;
        }
        if (numberB!=null && !numberB.isEmpty()) {
            numberB = " and numberB like '%" + numberB + "%'";
            queryStart += numberB;
        }
        if (startDate != null) {
            queryStart += " and a.startTime >= '" + startDate + "'";
        }
        if (endDate != null) {
            queryStart += " and a.startTime <= '" + endDate + "'";
        }
        Query q = getSessionFactory().getCurrentSession().
                createQuery(queryStart + queryEnd);
        q.setFirstResult(offset).setMaxResults(limit);
        return (List<Call>) q.list();
    }

}
