package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.Ip;
import com.elstele.bill.domain.IpSubnet;
import com.elstele.bill.utils.IpStatus;
import com.elstele.bill.utils.Status;
import org.hibernate.Query;
import org.omg.PortableInterceptor.ACTIVE;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IpDAOImpl extends CommonDAOImpl<Ip> implements IpDAO{

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
