package com.elstele.bill.dao.impl;


import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.UserStatisticDAO;
import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Radacct;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStatisticDAOImpl extends CommonDAOImpl<Account> implements UserStatisticDAO {
    @Override
    public List getUserActivityStatisticPerDay(String login, String startDate, String endDate){
        SQLQuery query = getSessionFactory().getCurrentSession().createSQLQuery(
                "select distinct( to_char(acctstoptime, 'dd') ) from radacct where " +
                        "username='"+login+"' and " +
                        "CAST(acctstoptime AS DATE) >=  CAST('"+startDate+"' AS DATE) and " +
                        "CAST(acctstoptime AS DATE) <= CAST('"+endDate+"' AS DATE);");
        return query.list();
    }

/*
    @Override
    public List<Radacct> getDailyStatistic(String login, String date) {
        Query query = getSessionFactory().getCurrentSession().createQuery(
                "from Radacct where username='"+login+"' and " +
                        "(acctstoptime >= '"+date+" 00:00:00' and acctstoptime <= '"+date+" 23:59:59')");
        return (List<Radacct>) query.list();
    }
*/

    public List<Radacct> getDailyStatistic(String login, String date) {
        String q = "select radacctid,username,text(nasipaddress) as nasipaddress,nasportid,acctstarttime,acctsessiontime,acctstoptime," +
                "text(framedipaddress) as framedipaddress,acctinputoctets,acctoutputoctets,acctterminatecause" +
                " from radacct where " +
                "username='"+login+"' and " +
                "CAST(acctstoptime AS DATE) =  CAST('"+date+"' AS DATE);";
        Query query = getSessionFactory().getCurrentSession().createSQLQuery(q)
                .setResultTransformer(Transformers.aliasToBean(Radacct.class));
        return (List<Radacct>) query.list();
    }
}
