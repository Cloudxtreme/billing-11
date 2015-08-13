package com.elstele.bill.datasrv;

import com.elstele.bill.form.DeviceForm;

import java.util.List;

public interface DeviceDataService {
    public List<DeviceForm> getDevices();

    public void addDevice(DeviceForm deviceForm);
}
