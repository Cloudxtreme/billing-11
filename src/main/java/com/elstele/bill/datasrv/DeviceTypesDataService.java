package com.elstele.bill.datasrv;


import com.elstele.bill.domain.DeviceTypes;
import com.elstele.bill.form.DeviceTypesForm;

import java.util.List;

public interface DeviceTypesDataService {

    public List<DeviceTypesForm> getDeviceTypes();
}
