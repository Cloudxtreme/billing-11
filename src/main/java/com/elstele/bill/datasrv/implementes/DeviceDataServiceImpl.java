package com.elstele.bill.datasrv.implementes;

import com.elstele.bill.assembler.DeviceAssembler;
import com.elstele.bill.dao.interfaces.DeviceDAO;
import com.elstele.bill.dao.interfaces.DeviceTypesDAO;
import com.elstele.bill.dao.interfaces.IpDAO;
import com.elstele.bill.datasrv.interfaces.DeviceDataService;
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

    @Autowired
    private DeviceTypesDAO deviceTypesDAO;

    @Autowired
    private IpDAO ipDAO;

    @Override
    @Transactional
    public List<DeviceForm> getDevices() {

        List<DeviceForm> result = new ArrayList<DeviceForm>();
        DeviceAssembler assembler = new DeviceAssembler(deviceTypesDAO, ipDAO);

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
        DeviceAssembler deviceAssembler = new DeviceAssembler(deviceTypesDAO, ipDAO);
        Device device = deviceAssembler.fromFormToBean(deviceForm);
        deviceDAO.create(device);
    }

    @Override
    @Transactional
    public void deleteDevice(Integer id){
        deviceDAO.delete(id);
    }

    @Override
    @Transactional
    public DeviceForm getById(Integer id){

        DeviceAssembler assembler = new DeviceAssembler(deviceTypesDAO, ipDAO);
        Device bean = deviceDAO.getById(id);
        DeviceForm result = assembler.fromBeanToForm(bean);
        return result;
    }

    @Override
    @Transactional
    public void updateDevice(DeviceForm deviceForm){
        DeviceAssembler assembler = new DeviceAssembler(deviceTypesDAO, ipDAO);
        Device bean = assembler.fromFormToBean(deviceForm);
        deviceDAO.update(bean);
    }

}
