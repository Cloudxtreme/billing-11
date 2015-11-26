package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.interfaces.USDRateDAO;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class USDRateDAOImpl implements USDRateDAO {

    @Autowired
    private SessionFactory sessionFactory;
    final static Logger log = org.apache.logging.log4j.LogManager.getLogger(USDRateDAOImpl.class);

    public void setUSDRateValue(Date rateDate, Double value) {
        String queryStr = "INSERT INTO public.usd_rate(date, value) VALUES('" + rateDate + "', '" + value + "')";
        try {
            sessionFactory.getCurrentSession().createSQLQuery(queryStr).executeUpdate();
            log.info("USD RATE is " + value + " and DATE is " + rateDate.toString());
        }catch(SQLGrammarException e){
            log.error("Problem with putting into the DB. Method setUSDRateValue");
        }
    }
}
