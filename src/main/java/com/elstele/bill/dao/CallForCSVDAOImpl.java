package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.CallForCSV;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

@Service
public class CallForCSVDAOImpl extends CommonDAOImpl<CallForCSV> implements CallForCSVDAO {

    @Override
    public void clearReportDataTable() {
        String hql = "delete from CallForCSV";
        Query query  = getSessionFactory().getCurrentSession().createQuery(hql);
        query.executeUpdate();
    }

}
