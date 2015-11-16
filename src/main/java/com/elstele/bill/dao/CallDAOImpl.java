package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.Call;
import com.elstele.bill.utils.CallTransformerDir;
import com.elstele.bill.utils.TempObjectForCallsRequestParam;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.LogManager;

@Service
public class CallDAOImpl extends CommonDAOImpl<Call> implements CallDAO {
    final static Logger log = org.apache.logging.log4j.LogManager.getLogger(CallDAOImpl.class);
    public List<Call> getCalls() {
        return null;
    }

    public Integer getCallsCount() {
        Query q = getSessionFactory().getCurrentSession().
                createQuery("select count(* ) from Call");
        Long res = (Long) q.uniqueResult();
        return res.intValue();
    }

    public Integer getCallsCountWithSearchValues(TempObjectForCallsRequestParam tempObjectForCallsRequestParam) {
        StringBuffer queryStart = new StringBuffer("select count(* ) from Call where 1=1 ");
        if (tempObjectForCallsRequestParam.getCallNumberA() != null && !tempObjectForCallsRequestParam.getCallNumberA().isEmpty()) {
            StringBuffer numberA = new StringBuffer("and numberA like '" + tempObjectForCallsRequestParam.getCallNumberA() + "%'");
            queryStart.append(numberA);
        }
        if (tempObjectForCallsRequestParam.getCallNumberB() != null && !tempObjectForCallsRequestParam.getCallNumberB().isEmpty()) {
            StringBuffer numberB = new StringBuffer(" and numberB like '" + tempObjectForCallsRequestParam.getCallNumberB() + "%'");
            queryStart.append(numberB);
        }
        if (tempObjectForCallsRequestParam.getStartDate() != null) {
            StringBuffer startDateString = new StringBuffer(" and a.startTime >= '" + tempObjectForCallsRequestParam.getStartDate() + "'");
            queryStart.append(startDateString);

        }
        if (tempObjectForCallsRequestParam.getEndDate() != null) {
            StringBuffer endDateString = new StringBuffer(" and a.startTime <= '" + tempObjectForCallsRequestParam.getEndDate() + "'");
            queryStart.append(endDateString);
        }
        Query q = getSessionFactory().getCurrentSession().
                createQuery(queryStart.toString());
        Long res = (Long) q.uniqueResult();
        return res.intValue();
    }

    public List<Call> getCallsList(int limit, int offset) {
        Query q = getSessionFactory().getCurrentSession().
                createQuery("select a from Call a order by a.startTime");
        q.setFirstResult(offset).setMaxResults(limit);

        return (List<Call>) q.list();
    }


    public List<Call> callsListSelectionBySearch(int limit, int offset, String numberA, String numberB, Date startDate, Date endDate) {
        StringBuffer queryStart = new StringBuffer("select a from Call a where 1=1 ");
        StringBuffer queryEnd = new StringBuffer(" order by a.startTime");
        if (numberA != null && !numberA.isEmpty()) {
            numberA = "and numberA like '" + numberA + "%'";
            queryStart.append(numberA);
        }
        if (numberB != null && !numberB.isEmpty()) {
            numberB = " and numberB like '" + numberB + "%'";
            queryStart.append(numberB);
        }
        if (startDate != null) {
            StringBuffer startDateString = new StringBuffer(" and a.startTime >= '" + startDate + "'");
            queryStart.append(startDateString);

        }
        if (endDate != null) {
            StringBuffer endDateString = new StringBuffer(" and a.startTime <= '" + endDate + "'");
            queryStart.append(endDateString);
        }
        Query q = getSessionFactory().getCurrentSession().
                createQuery(queryStart.append(queryEnd).toString());
        q.setFirstResult(offset).setMaxResults(limit);
        return (List<Call>) q.list();
    }

    public List<String> getUniqueNumberAFromCalls(Date startTime, Date finishTime) {
        SQLQuery createSQLQuery = getSessionFactory().getCurrentSession().createSQLQuery("select distinct numberA from calls " +
                "where startTime >='" + startTime + "' and startTime <= '" + finishTime + "' and costTotal Is not null order by numberA");
        return (List<String>) createSQLQuery.list();
    }

    public List<CallTransformerDir> getCallByNumberA(String numberA, Date startTime, Date endTime) {
        String q = "Select calls.numberb as numberB, calls.startTime as startTime, calls.duration as duration, calls.costTotal as costTotal, directions.description as description," +
                " directions.prefix as prefix from calls, directions " +
                "where calls.callDirectionId = directions.id and calls.startTime >= '"+startTime +"' and calls.startTime <= '"+ endTime+"' and " +
                "calls.numberA = '"+ numberA+"' order by calls.startTime";
        Query query = getSessionFactory().getCurrentSession().createSQLQuery(q)
                .setResultTransformer(Transformers.aliasToBean(CallTransformerDir.class));
        return (List<CallTransformerDir>) query.list();
    }

    public List<String> getUniqueNumberAFromCallsWithTrunk(Date startTime, Date finishTime, String outputTrunk) {
        SQLQuery createSQLQuery = getSessionFactory().getCurrentSession().createSQLQuery("select distinct numberA from calls " +
                "where startTime >='" + startTime + "' and startTime <= '" + finishTime + "' and outputTrunk ='" + outputTrunk + "' and costTotal Is not null order by numberA");
        return (List<String>) createSQLQuery.list();
    }

    public List<CallTransformerDir> getCallByNumberAWithTrunk(String numberA, Date startTime, Date finishTime, String outputTrunk) {
        String q = "Select calls.numberb as numberB, calls.startTime as startTime, calls.duration as duration, calls.costTotal as costTotal, directions.description as description," +
                " directions.prefix as prefix from calls, directions " +
                "where calls.callDirectionId = directions.id and calls.startTime >= '"+startTime +"' and calls.startTime <= '"+ finishTime+"' and " +
                "calls.numberA = '"+ numberA+"' and calls.outputTrunk='"+outputTrunk +"' order by calls.startTime";
        Query query = getSessionFactory().getCurrentSession().createSQLQuery(q)
                .setResultTransformer(Transformers.aliasToBean(CallTransformerDir.class));
        return (List<CallTransformerDir>) query.list();
    }

    public List<String> getUniqueLocalNumberAFromCalls(Date startTime, Date finishTime) {
        SQLQuery createSQLQuery = getSessionFactory().getCurrentSession().createSQLQuery("select distinct numberA from calls " +
                "where startTime >='" + startTime + "' and startTime <= '" + finishTime + "' and ( numberA like ('7895%') or numberA like ('7896%') or numberA like ('7897%'))" +
                "   and costTotal Is null order by numberA");
        return (List<String>) createSQLQuery.list();
    }

    public List<Call> getLocalCalls(String numberA, Date startTime, Date endTime) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from Call  where callDirectionId is null and startTime >='" + startTime
                + "' and startTime <='" + endTime + "' and (numberA ='" + numberA + "' or numberA = '048" + numberA + "')  order by startTime");
        return (List<Call>) query.list();
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

    public List<Integer> getUnbilledCallIds() {
        Query q = getSessionFactory().getCurrentSession().
                createQuery("select c.id from Call c where c.costTotal is null AND c.numberB like ?  " +
                        "order by c.id");
        q.setString(0, "0%");
        return (List<Integer>)q.list();
    }

    public List<String> getYearsList() {
        try {
            SQLQuery query = getSessionFactory().getCurrentSession().createSQLQuery("Select DISTINCT DATE_PART('year', starttime) as YEAR from calls ORDER BY DATE_PART('year', starttime)")
                    .addScalar("YEAR", new StringType());
            List<String> result = (List<String>)query.list();
            log.info("Date selecting from DB is successed");
            return result;
        }catch(SQLGrammarException e){
            log.error(e);
            return new ArrayList<>();
        }
    }

    public List<Integer> getCallIdsWithNullCostTotal() {
        Query q = getSessionFactory().getCurrentSession().
                createQuery("select c.id from Call c where c.costTotal is null "+
                        "order by c.id");
        return (List<Integer>)q.list();
    }
}
