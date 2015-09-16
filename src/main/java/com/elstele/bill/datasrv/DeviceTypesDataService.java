package com.elstele.bill.datasrv;


import com.elstele.bill.domain.DeviceTypes;
import com.elstele.bill.form.DeviceTypesForm;

import java.util.List;

public interface DeviceTypesDataService {

    public List<DeviceTypesForm> getDeviceTypes();

    public void addDeviceType(DeviceTypesForm deviceTypesForm);

    public DeviceTypesForm getById(Integer id);

    public void deleteDeviceType(Integer id);
}
