package com.elstele.bill.dao.impl;


import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.UserRoleDAO;
import com.elstele.bill.dao.interfaces.UserStatisticDAO;
import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Radacct;
import com.elstele.bill.domain.UserRole;
import com.elstele.bill.utils.CustomizeCalendar;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserStatisticDAOImpl extends CommonDAOImpl<Account> implements UserStatisticDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List getUserActivityStatisticPerDay(Integer login, String startDate, String endDate){
        SQLQuery query = getSessionFactory().getCurrentSession().createSQLQuery(
                "select distinct( to_char(acctstoptime, 'dd') ) from radacct where " +
                        "username='"+login+"' and " +
                        "CAST(acctstoptime AS DATE) >=  CAST('"+startDate+"' AS DATE) and " +
                        "CAST(acctstoptime AS DATE) <= CAST('"+endDate+"' AS DATE);");
        return query.list();
    }

    @Override
    public List<Radacct> getDailyStatistic(Integer login, String date) {
        Query query = getSessionFactory().getCurrentSession().createQuery(
                "from Radacct where username='"+login+"' and " +
                        "(acctstoptime >= '"+date+" 00:00:00' and acctstoptime <= '"+date+" 23:59:59')");
        return (List<Radacct>) query.list();
    }
}
