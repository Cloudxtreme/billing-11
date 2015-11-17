package com.elstele.bill.dao.impl;


import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.IpSubnetDAO;
import com.elstele.bill.domain.IpSubnet;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IpSubnetDAOImpl extends CommonDAOImpl<IpSubnet> implements IpSubnetDAO {

    @Override
    public List<IpSubnet> getSubnets() {

        Query query = getSessionFactory().getCurrentSession().createQuery("from IpSubnet");
        return (List<IpSubnet>)query.list();
    }
}
