package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.Call;
import com.elstele.bill.utils.TempObjectForCallsRequestParam;
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
    public Integer getCallsCountWithSearchValues(TempObjectForCallsRequestParam tempObjectForCallsRequestParam) {
        StringBuffer queryStart = new StringBuffer("select count(* ) from Call where 1=1 ");
        if (tempObjectForCallsRequestParam.getCallNumberA()!=null && !tempObjectForCallsRequestParam.getCallNumberA().isEmpty()) {
            StringBuffer numberA = new StringBuffer("and numberA like '%" + tempObjectForCallsRequestParam.getCallNumberA() + "%'");
            queryStart.append(numberA);
        }
        if (tempObjectForCallsRequestParam.getCallNumberB()!=null && !tempObjectForCallsRequestParam.getCallNumberB().isEmpty()) {
            StringBuffer numberB = new StringBuffer(" and numberB like '%" + tempObjectForCallsRequestParam.getCallNumberB() + "%'");
            queryStart.append(numberB);
        }
        if (tempObjectForCallsRequestParam.getStartDate() != null) {
            StringBuffer startDateString = new StringBuffer(" and a.startTime >= '" + tempObjectForCallsRequestParam.getStartDate() + "'");
            queryStart.append(startDateString);

        }
        if (tempObjectForCallsRequestParam.getEndDate() != null) {
            StringBuffer endDateString =new StringBuffer( " and a.startTime <= '" + tempObjectForCallsRequestParam.getEndDate() + "'");
            queryStart.append(endDateString);
        }
        Query q = getSessionFactory().getCurrentSession().
                createQuery(queryStart.toString());
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


    @Override
    public List<Call> callsListSelectionBySearch(int limit, int offset, String numberA, String numberB, Date startDate, Date endDate) {
        StringBuffer queryStart = new StringBuffer("select a from Call a where 1=1 ");
        StringBuffer queryEnd =  new StringBuffer(" order by a.startTime");
        if (numberA!=null && !numberA.isEmpty()) {
            numberA = "and numberA like '%" + numberA + "%'";
            queryStart.append(numberA);
        }
        if (numberB!=null && !numberB.isEmpty()) {
            numberB = " and numberB like '%" + numberB + "%'";
            queryStart.append(numberB);
        }
        if (startDate != null) {
            StringBuffer startDateString = new StringBuffer(" and a.startTime >= '" + startDate + "'");
            queryStart.append(startDateString);

        }
        if (endDate != null) {
            StringBuffer endDateString =new StringBuffer( " and a.startTime <= '" + endDate + "'");
            queryStart.append(endDateString);
        }
        Query q = getSessionFactory().getCurrentSession().
                createQuery(queryStart.append(queryEnd).toString());
        q.setFirstResult(offset).setMaxResults(limit);
        return (List<Call>) q.list();
    }

}
