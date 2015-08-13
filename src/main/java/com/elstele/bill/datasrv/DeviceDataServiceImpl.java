package com.elstele.bill.datasrv;

import com.elstele.bill.assembler.DeviceAssembler;
import com.elstele.bill.dao.DeviceDAO;
import com.elstele.bill.form.DeviceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import com.elstele.bill.domain.Device;

@Service
public class DeviceDataServiceImpl implements DeviceDataService {

    @Autowired
    private DeviceDAO deviceDAO;

    @Override
    @Transactional
    public List<DeviceForm> getDevices() {

        List<DeviceForm> result = new ArrayList<DeviceForm>();
        DeviceAssembler assembler = new DeviceAssembler();

        List<Device> beans = deviceDAO.getDevices();
        for (Device curBean : beans) {
            DeviceForm curForm = assembler.fromBeanToForm(curBean);
            result.add(curForm);
        }
        return result;
    }

    @Override
    @Transactional
    public void addDevice(DeviceForm deviceForm){
        DeviceAssembler deviceAssembler = new DeviceAssembler();
        Device device = deviceAssembler.fromFormToBean(deviceForm);
        deviceDAO.create(device);
    }
}
