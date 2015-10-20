package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.CallForCSV;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CallForCSVDAOImpl extends CommonDAOImpl<CallForCSV> implements CallForCSVDAO {

    @Override
    public void clearReportDataTable() {
        String hql = "delete from CallForCSV";
        Query query  = getSessionFactory().getCurrentSession().createQuery(hql);
        query.executeUpdate();
    }

    @Override
    public List<CallForCSV> getUniqueNumberA(Date startTime, Date finishTime) {

        Query query = getSessionFactory().getCurrentSession().createQuery("from CallForCSV  where numberA IN (select distinct numberA from CallForCSV " +
                "where startTime >='"+ startTime + "' and startTime <= '" + finishTime +"' and costCallTotal IS NOT NULL order by numberA)");
        return (List<CallForCSV>)query.list();
    }

    @Override
    public List<CallForCSV> getCallForCSVByNumberA(String numberA, Date startTime, Date endTime) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from CallForCSV  where numberA =' "+ numberA+"' and startTime >='"+ startTime
                + "'and startTime <='" + endTime + "'order by startTime");
        return (List<CallForCSV>)query.list();
    }

    @Override
    public Date getDateInterval() {
       Query query = getSessionFactory().getCurrentSession().createQuery("select startTime from CallForCSV");
       query.setMaxResults(1);
       return (Date)query.uniqueResult();
    }

}
