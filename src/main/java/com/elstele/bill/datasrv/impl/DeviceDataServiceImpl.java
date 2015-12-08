package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.DeviceAssembler;
import com.elstele.bill.assembler.StreetAssembler;
import com.elstele.bill.dao.interfaces.DeviceDAO;
import com.elstele.bill.dao.interfaces.DeviceTypesDAO;
import com.elstele.bill.dao.interfaces.IpDAO;
import com.elstele.bill.datasrv.interfaces.DeviceDataService;
import com.elstele.bill.domain.Street;
import com.elstele.bill.form.DeviceForm;
import com.elstele.bill.form.StreetForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
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
    public Integer addDevice(DeviceForm deviceForm){
        DeviceAssembler deviceAssembler = new DeviceAssembler(deviceTypesDAO, ipDAO);
        Device device = deviceAssembler.fromFormToBean(deviceForm);
        return deviceDAO.create(device);
    }

    @Override
    @Transactional
    public void deleteDevice(Integer id){
        deviceDAO.setStatusDelete(id);
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
    public List<Integer> getDeviceFreePorts(Integer id){
        List<Integer> freePorts = new ArrayList();
        Device device = deviceDAO.getById(id);
        List<Integer> usedPorts = deviceDAO.getDeviceUsagePorts(id);
        for (int i=1; i<=device.getDeviceTypes().getPortsNumber(); i++){
            if( usedPorts==null || !usedPorts.contains(i) )
                freePorts.add(i);
        }
        return freePorts;
    }

    @Override
    @Transactional
    public void updateDevice(DeviceForm deviceForm){
        DeviceAssembler assembler = new DeviceAssembler(deviceTypesDAO, ipDAO);
        Device bean = assembler.fromFormToBean(deviceForm);
        deviceDAO.update(bean);
    }

    @Override
    @Transactional
    public List<StreetForm> getStreets(){
        StreetAssembler assembler = new StreetAssembler();
        List<Street> streets = deviceDAO.getStreets();
        List<StreetForm> result = new ArrayList<>();
        for(Street street : streets){
            result.add(assembler.fromBeanToForm(street));
        }
        return result;
    }

}
