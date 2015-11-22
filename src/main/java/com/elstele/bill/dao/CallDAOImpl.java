package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.Call;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class CallDAOImpl extends CommonDAOImpl<Call> implements CallDAO {

    public List<Call> getCalls() {
        return null;
    }

    public Integer getCallsCount() {
        Query q = getSessionFactory().getCurrentSession().
                createQuery("select count(* ) from Call");
        Long res = (Long)q.uniqueResult();
        return res.intValue();
    }

    public List<Call> getCallsList(int limit, int offset) {
        Query q = getSessionFactory().getCurrentSession().
                createQuery("select a from Call a order by a.numberA");
        q.setFirstResult(offset).setMaxResults(limit);

        return (List<Call>)q.list();
    }

    public Integer getUnbilledCallsCount() {
        Query q = getSessionFactory().getCurrentSession().
        createSQLQuery("SELECT count(*) from calls where costtotal IS NULL and numberb like ('0%')");
        return ((BigInteger)q.uniqueResult()).intValue();
    }

    public List<Integer> getUnbilledCallIds(int limit, int offset) {
        Query q = getSessionFactory().getCurrentSession().
                createQuery("select c.id from Call c where c.costTotal is null AND c.numberB like ?  " +
                        "order by c.id");
        q.setString(0, "0%");
        q.setFirstResult(offset).setMaxResults(limit);
        return (List<Integer>)q.list();
    }
}
