package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.StreetDAO;
import com.elstele.bill.domain.Street;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StreetDAOImpl extends CommonDAOImpl<Street> implements StreetDAO {

    @Override
    public List<Street> getListOfStreets(String query){
        Query q = getSessionFactory().getCurrentSession().
                createQuery("from Street");
        return q.list();
    }
}
