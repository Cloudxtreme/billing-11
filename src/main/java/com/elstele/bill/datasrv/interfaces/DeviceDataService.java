package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.form.DeviceForm;

import java.util.List;

public interface DeviceDataService {
    public List<DeviceForm> getDevices();

    public void addDevice(DeviceForm deviceForm);

    public void deleteDevice(Integer id);

    public DeviceForm getById(Integer id);

    public void updateDevice(DeviceForm deviceForm);
}
