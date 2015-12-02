package com.elstele.bill.datasrv.interfaces;


import com.elstele.bill.form.DeviceTypesForm;

import java.util.List;

public interface DeviceTypesDataService {

    public List<DeviceTypesForm> getDeviceTypes();

    public Integer addDeviceType(DeviceTypesForm deviceTypesForm);

    public DeviceTypesForm getById(Integer id);

    public void deleteDeviceType(Integer id);

    public void updateDeviceTypes(DeviceTypesForm deviceTypesForm);
}
