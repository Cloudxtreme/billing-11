package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.DeviceTypesDAO;
import com.elstele.bill.domain.DeviceTypes;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceTypesDAOImpl extends CommonDAOImpl<DeviceTypes> implements DeviceTypesDAO {
    
    @Override
    public List<DeviceTypes> getDeviceTypes(){


        Query query = getSessionFactory().getCurrentSession().createQuery("from DeviceTypes");
        return (List<DeviceTypes>)query.list();
    }

}
