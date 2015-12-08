package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.form.DeviceForm;
import com.elstele.bill.form.StreetForm;

import java.util.List;

public interface DeviceDataService {
    public List<DeviceForm> getDevices();

    public Integer addDevice(DeviceForm deviceForm);

    public void deleteDevice(Integer id);

    public DeviceForm getById(Integer id);

    public List<Integer> getDeviceFreePorts(Integer id);

    public void updateDevice(DeviceForm deviceForm);

    public List<StreetForm> getStreets();
}
