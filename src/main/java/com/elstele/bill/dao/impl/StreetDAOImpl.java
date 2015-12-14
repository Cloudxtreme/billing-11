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
    public List<Street> getListOfStreets(){
        Query q = getSessionFactory().getCurrentSession().
                createQuery("from Street");
        return q.list();
    }

    @Override
    public Integer getStreetIDByStreetName(String streetName){
        Query q = getSessionFactory().getCurrentSession().
                createQuery("select s.id from Street s where s.name = :streetName");
        q.setParameter("streetName", streetName);
        return (Integer)q.uniqueResult();
    }
}
