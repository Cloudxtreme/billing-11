package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.DeviceDAO;
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

}
