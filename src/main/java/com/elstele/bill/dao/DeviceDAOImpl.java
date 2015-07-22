package com.elstele.bill.dao;

import com.elstele.bill.dao.DeviceDAO;
import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.Device;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DeviceDAOImpl extends CommonDAOImpl<Device> implements DeviceDAO {

    @Override
    public List<Device> getDevices() {
        String rd = "SELECT * FROM Device";
        Query query = getSessionFactory().getCurrentSession().createQuery(rd);
        return (List<Device>)query.list();
    }
}
