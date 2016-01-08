package com.elstele.bill.datasrv.interfaces;


import com.elstele.bill.form.DeviceTypesForm;

import java.util.HashMap;
import java.util.List;

public interface DeviceTypesDataService {

    public List<DeviceTypesForm> getDeviceTypes();

    public HashMap<Integer, String> getDeviceTypesAsMap();

    public Integer addDeviceType(DeviceTypesForm deviceTypesForm);

    public DeviceTypesForm getById(Integer id);

    public void deleteDeviceType(Integer id);

    public void updateDeviceTypes(DeviceTypesForm deviceTypesForm);
}
