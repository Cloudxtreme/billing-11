package com.elstele.bill.dao.interfaces;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.Device;

import java.util.List;


public interface DeviceDAO extends CommonDAO<Device>{

    public List<Device> getDevices();

}
