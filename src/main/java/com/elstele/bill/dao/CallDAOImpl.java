package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.Call;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

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
        Long res = (Long)q.uniqueResult();
        return res.intValue();
    }

    @Override
    public List<Call> getCallsList(int limit, int offset) {
        Query q = getSessionFactory().getCurrentSession().
                createQuery("select a from Call a order by a.numberA");
        q.setFirstResult(offset).setMaxResults(limit);

        return (List<Call>)q.list();
    }

}
