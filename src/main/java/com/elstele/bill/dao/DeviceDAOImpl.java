package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.Device;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DeviceDAOImpl extends CommonDAOImpl<Device> implements DeviceDAO {

    @Override
    public List<Device> getDevices() {
        Query query = getSessionFactory().getCurrentSession().createQuery("from Device");
        return (List<Device>)query.list();
    }

    @Override
    public List<Integer> getDeviceUsagePorts(Integer id){
        String hql = "select service.port from ServiceInternet as service where device.id = :id and status  <> 'DELETED'";
        Query query = getSessionFactory().getCurrentSession().createQuery(hql)
                .setParameter("id", id);
        if (!query.list().isEmpty()){
            return (List<Integer>)query.list();
        }
        return null;
    }
}
