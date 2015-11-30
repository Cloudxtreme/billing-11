package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.CallForCSVDAO;
import com.elstele.bill.domain.CallForCSV;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CallForCSVDAOImpl extends CommonDAOImpl<CallForCSV> implements CallForCSVDAO {

    @Override
    public List<String> getUniqueNumberAWithProvider(Date startTime, Date finishTime, String provider) {
        SQLQuery createSQLQuery = getSessionFactory().getCurrentSession().createSQLQuery("select distinct numberA from callForCSV " +
                "where startTime >='" + startTime + "' and startTime <= '" + finishTime + "' and provider ='" + provider + "' and costCallTotal Is not null order by numberA");
        return (List<String>) createSQLQuery.list();
    }

    @Override
    public List<String> getUniqueNumberA(Date startTime, Date finishTime) {
        SQLQuery createSQLQuery = getSessionFactory().getCurrentSession().createSQLQuery("select distinct numberA from callForCSV " +
                "where startTime >='" + startTime + "' and startTime <= '" + finishTime + "' and costCallTotal Is not null order by numberA");
        return (List<String>) createSQLQuery.list();
    }

    @Override
    public List<CallForCSV> getCallForCSVByNumberA(String numberA, Date startTime, Date endTime) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from CallForCSV  where numberA ='" + numberA + "' and startTime >='" + startTime
                + "' and startTime <='" + endTime + "' order by startTime");
        return (List<CallForCSV>) query.list();
    }

    @Override
    public List<CallForCSV> getCallForCSVByNumberAWithProvider(String numberA, Date startTime, Date endTime, String provider) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from CallForCSV  where numberA ='" + numberA + "' and startTime >='" + startTime
                + "' and startTime <='" + endTime + "' and provider ='" + provider + "' order by startTime");
        return (List<CallForCSV>) query.list();
    }

    @Override
    public String getDescriptionFromDirections(String dirPrefix) {
        Query query = getSessionFactory().getCurrentSession().createSQLQuery("Select description from directions where prefix='" + dirPrefix + "' ");
        return (String)query.uniqueResult();
    }

}
