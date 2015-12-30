package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.ExternalPaymentDAO;
import com.elstele.bill.domain.ExternalPaymentTransaction;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import java.util.List;

/**
 * Created by ivan on 15/12/27.
 */
public class ExternalPaymentDAOImpl extends CommonDAOImpl<ExternalPaymentTransaction> implements ExternalPaymentDAO {

    public List<ExternalPaymentTransaction> getExtPaymentList() {
        Session session = getSessionFactory().getCurrentSession();
        setFilter(session, "showActive");
        Query q = session.
                createQuery("select tr from ExternalPaymentTransaction a where status <> 'DELETED'" +
                        "order by tr.timestamp ASC");

        return (List<ExternalPaymentTransaction>)q.list();
    }
}
