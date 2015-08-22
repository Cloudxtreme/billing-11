package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.Ip;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IpDAOImpl extends CommonDAOImpl<Ip> implements IpDAO{

    @Override
    public List<Ip> getIpAddressList() {
        Query query = getSessionFactory().getCurrentSession().createQuery("from Ip");
        return (List<Ip>)query.list();
    }
}
