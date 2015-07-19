package com.elstele.bill.dao;

import com.elstele.bill.dao.DeviceDAO;
import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.Device;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

@Service
public class DeviceDAOImpl extends CommonDAOImpl<Device> implements DeviceDAO {

    @Override
    public Device getDeviceFromDB() {
        String rd = "SELECT * FROM Device";
        Query query = getSessionFactory().getCurrentSession().createQuery(rd);
        
        return null;
    }
}
