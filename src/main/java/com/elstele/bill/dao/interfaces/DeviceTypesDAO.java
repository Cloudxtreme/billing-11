package com.elstele.bill.dao.interfaces;


import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.DeviceTypes;

import java.util.List;

public interface DeviceTypesDAO extends CommonDAO<DeviceTypes> {

    public List<DeviceTypes> getDeviceTypes();

}