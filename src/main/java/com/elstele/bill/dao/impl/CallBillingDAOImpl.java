package com.elstele.bill.dao.impl;

import com.elstele.bill.billparts.CallBillRule;
import com.elstele.bill.billparts.CallDirection;
import com.elstele.bill.dao.interfaces.CallBillingDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CallBillingDAOImpl  implements CallBillingDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public CallDirection getCallDirection(String numberB, Date callDate) {

        String query_find_end = createSQLSearchConditionForDirection(numberB);

        Query query = sessionFactory.getCurrentSession().createSQLQuery(
                "select directions.id, directions.prefix, tariff_zones.tarif, tariff_zones.tarif_pref, " +
                        "tariff_zones.dollar, tariff_zones.pref_profile " +
                        "from directions, tariff_zones " +
                        "where directions.tarif_zone = tariff_zones.zone_id " +
                        "AND (directions.validfrom <= '" + callDate + "' or directions.validfrom IS NULL ) AND (directions.validto >= '" + callDate + "'  OR directions.validto IS NULL )" +
                        "and directions.prefix IS NOT NULL and (" + query_find_end + ") ")
                .setResultTransformer(Transformers.aliasToBean(CallDirection.class));
        List <CallDirection> dbResult = query.list();

        CallDirection result = findLongestPrefixMatch(dbResult);
        return result;
    }

    private CallDirection findLongestPrefixMatch(List<CallDirection> dbResult) {
        CallDirection longMatchDirection = new CallDirection();
        longMatchDirection.setPrefix("");

        for (CallDirection curDirection : dbResult){
            int curPrefixLength = curDirection.getPrefix().length();
            int longMatchLength = longMatchDirection.getPrefix().length();
            if (curPrefixLength > longMatchLength){
                longMatchDirection = curDirection;
            }
        }
        return longMatchDirection;
    }

    public List<CallBillRule> getCallBillingRule(int billingProfile, Date callDate) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(
                "select day_of_month as dayOfMonth, month, day_of_week as dayOfWeek, starttime as startTime, " +
                        "finishtime as finishTime, tarif " +
                        "from preference_rules where profile_id = :billProfile AND (validfrom <= '" + callDate+ "' or validfrom IS NULL ) AND (validto >= '"+callDate+"' OR validto IS NULL )" +
                        " order by rule_priority")
                .setParameter("billProfile", billingProfile)
                .setResultTransformer(Transformers.aliasToBean(CallBillRule.class));
        List <CallBillRule> dbResult = query.list();
        return dbResult;
    }

    public float getUsdRateForCall(Date date) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(
                "select value from usd_rate where date <= :calldate ORDER BY DATE DESC")
                .setMaxResults(1)
                .setDate("calldate", date);
        Float rate = (Float)query.uniqueResult();
        return rate;
    }

    private String createSQLSearchConditionForDirection(String numberB) {
        StringBuffer resBuff =  new StringBuffer();
        String prefix = numberB.substring(0, numberB.length()-3);

        while(prefix.length() > 2){
            resBuff.append("directions.prefix ='" + prefix + "' OR ");
            prefix = prefix.substring(0, prefix.length()-1);
        }

        resBuff.delete(resBuff.length()-4, resBuff.length());
        return resBuff.toString();
    }
}
