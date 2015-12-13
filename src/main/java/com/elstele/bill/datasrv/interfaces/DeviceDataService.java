package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.domain.Street;
import com.elstele.bill.form.DeviceForm;
import com.elstele.bill.utils.Enums.ResponseToAjax;

import java.util.List;

public interface DeviceDataService {
    public List<DeviceForm> getDevices();

    public ResponseToAjax addDevice(DeviceForm deviceForm);

    public ResponseToAjax deleteDevice(Integer id);

    public DeviceForm getById(Integer id);

    public List<Integer> getDeviceFreePorts(Integer id);

    public ResponseToAjax updateDevice(DeviceForm deviceForm);

}
