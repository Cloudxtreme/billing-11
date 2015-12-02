package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.IpDAO;
import com.elstele.bill.domain.Ip;
import com.elstele.bill.utils.Enums.IpStatus;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IpDAOImpl extends CommonDAOImpl<Ip> implements IpDAO {

    @Override
    public List<Ip> getIpAddressList() {
        Query query = getSessionFactory().getCurrentSession().createQuery("from Ip");
        return (List<Ip>)query.list();
    }


    @Override
    public void setStatusById(IpStatus ipStatus, Integer id) {
        Ip ip = new Ip();
        ip = getById(id);
        ip.setIpStatus(ipStatus);
        update(ip);
    }

    @Override
    public List<Ip> getIpAddressListBySubnetId(Integer id){
        Query query = getSessionFactory().getCurrentSession().createQuery("from Ip where ipSubnet.id is "+id);
        return (List<Ip>)query.list();
    }



}
