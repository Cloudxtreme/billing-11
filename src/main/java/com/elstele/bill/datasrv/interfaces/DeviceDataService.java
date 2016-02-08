package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.domain.Street;
import com.elstele.bill.form.DeviceForm;
import com.elstele.bill.utils.Enums.ResponseToAjax;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface DeviceDataService {
    public List<DeviceForm> getDevices();

    public Integer addDevice(DeviceForm deviceForm, HttpSession session);

    public ResponseToAjax deleteDevice(Integer id, HttpSession session);

    public DeviceForm getById(Integer id);

    public List<Integer> getDeviceFreePorts(Integer id);

    public void updateDevice(DeviceForm deviceForm, HttpSession session);

}
