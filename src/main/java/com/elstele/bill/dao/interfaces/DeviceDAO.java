package com.elstele.bill.dao.interfaces;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.Address;
import com.elstele.bill.domain.Device;
import com.elstele.bill.domain.Street;

import java.util.List;


public interface DeviceDAO extends CommonDAO<Device>{

    public List<Device> getDevices();
    public List<Integer> getDeviceUsagePorts(Integer id);
    public Integer create(Device device, String changerName);
    public void update(Device device, String changerName);
}
