package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.DeviceAssembler;
import com.elstele.bill.dao.interfaces.DeviceDAO;
import com.elstele.bill.dao.interfaces.DeviceTypesDAO;
import com.elstele.bill.dao.interfaces.IpDAO;
import com.elstele.bill.dao.interfaces.StreetDAO;
import com.elstele.bill.datasrv.interfaces.DeviceDataService;
import com.elstele.bill.datasrv.interfaces.IpDataService;
import com.elstele.bill.domain.Street;
import com.elstele.bill.form.DeviceForm;
import com.elstele.bill.utils.Enums.IpStatus;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.utils.Enums.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
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
    @Autowired
    private StreetDAO streetDAO;
    @Autowired
    IpDataService ipDataService;
    final static Logger log = LogManager.getLogger(DeviceDataServiceImpl.class);

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
    public ResponseToAjax addDevice(DeviceForm deviceForm) {
        DeviceAssembler deviceAssembler = new DeviceAssembler(deviceTypesDAO, ipDAO);
        Device device = deviceAssembler.fromFormToBean(deviceForm);
        device.setStatus(Status.ACTIVE);
        try{
            deviceDAO.create(device);
            return ResponseToAjax.SUCCESS;
        } catch (ConstraintViolationException e){
            log.error(e + " Method addDevice");
            return ResponseToAjax.ERROR;
        }
    }

    @Override
    @Transactional
    public ResponseToAjax deleteDevice(Integer id) {
        try {
            DeviceForm deviceForm = getById(id);
            ipDataService.setStatus(deviceForm.getIpForm().getId(), IpStatus.FREE);
            deviceDAO.setStatusDelete(id);
            return ResponseToAjax.SUCCESS;
        } catch (Exception e) {
            log.error(e + " Method deleteDevice");
            return ResponseToAjax.ERROR;
        }
    }

    @Override
    @Transactional
    public DeviceForm getById(Integer id) {
        DeviceAssembler assembler = new DeviceAssembler(deviceTypesDAO, ipDAO);
        Device bean = deviceDAO.getById(id);
        DeviceForm result = assembler.fromBeanToForm(bean);
        return result;
    }

    @Override
    @Transactional
    public List<Integer> getDeviceFreePorts(Integer id) {
        List<Integer> freePorts = new ArrayList();
        Device device = deviceDAO.getById(id);
        List<Integer> usedPorts = deviceDAO.getDeviceUsagePorts(id);
        for (int i = 1; i <= device.getDeviceType().getPortsNumber(); i++) {
            if (usedPorts == null || !usedPorts.contains(i))
                freePorts.add(i);
        }
        return freePorts;
    }

    @Override
    @Transactional
    public ResponseToAjax updateDevice(DeviceForm deviceForm) {
        try {
            DeviceAssembler assembler = new DeviceAssembler(deviceTypesDAO, ipDAO);
            Device bean = assembler.fromFormToBean(deviceForm);
            deviceDAO.update(bean);
            return ResponseToAjax.SUCCESS;
        }catch(HibernateException e ){
            log.error(e + " Method updateDevice");
            return ResponseToAjax.ERROR;
        }
    }

}
