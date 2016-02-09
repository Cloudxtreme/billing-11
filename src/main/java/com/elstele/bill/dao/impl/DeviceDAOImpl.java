package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.AuditedObjectDAO;
import com.elstele.bill.dao.interfaces.DeviceDAO;
import com.elstele.bill.domain.Device;
import com.elstele.bill.domain.Street;
import com.elstele.bill.utils.Enums.ObjectOperationType;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DeviceDAOImpl extends CommonDAOImpl<Device> implements DeviceDAO {
    @Autowired
    AuditedObjectDAO auditedObjectDAO;

    @Override
    public List<Device> getDevices() {
        Query query = getSessionFactory().getCurrentSession().createQuery("from Device  where status <> 'DELETED'");
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

    @Override
    public Integer create(Device device, String changerName){
        int id = (Integer)getSessionFactory().getCurrentSession().save(device);
        auditedObjectDAO.create(device, ObjectOperationType.CREATE, changerName);
        return id;
    }

    @Override
    public void update(Device device, String changerName) {
        getSessionFactory().getCurrentSession().saveOrUpdate(device);
        getSessionFactory().getCurrentSession().flush();
        auditedObjectDAO.create(device, ObjectOperationType.UPDATE, changerName);
    }

}
