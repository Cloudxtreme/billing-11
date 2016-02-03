package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.DeviceAssembler;
import com.elstele.bill.dao.interfaces.DeviceDAO;
import com.elstele.bill.dao.interfaces.DeviceTypesDAO;
import com.elstele.bill.dao.interfaces.IpDAO;
import com.elstele.bill.dao.interfaces.StreetDAO;
import com.elstele.bill.datasrv.interfaces.DeviceDataService;
import com.elstele.bill.datasrv.interfaces.IpDataService;
import com.elstele.bill.datasrv.interfaces.StreetDataService;
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
    @Autowired
    StreetDataService streetDataService;
    final static Logger LOGGER = LogManager.getLogger(DeviceDataServiceImpl.class);

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
    public Integer addDevice(DeviceForm deviceForm) {
        gettingCorrectIDForCurrentFormAndCurrentStreet(deviceForm);
        DeviceAssembler deviceAssembler = new DeviceAssembler(deviceTypesDAO, ipDAO);
        Device device = deviceAssembler.fromFormToBean(deviceForm);
        device.setStatus(Status.ACTIVE);
        try{
            int creatingId = deviceDAO.create(device);
            ipDataService.setStatus(deviceForm.getIpForm().getId(), IpStatus.USED);
            updateStreetListAfterInsert(deviceForm);
            LOGGER.info("Device " + deviceForm.getName() + " added successfully");
            return creatingId;
        } catch (ConstraintViolationException e){
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    @Transactional
    public void updateDevice(DeviceForm deviceForm) {
        try {
            gettingCorrectIDForCurrentFormAndCurrentStreet(deviceForm);
            DeviceAssembler assembler = new DeviceAssembler(deviceTypesDAO, ipDAO);
            Device bean = assembler.fromFormToBean(deviceForm);
            deviceDAO.update(bean);
            ipDataService.setStatus(deviceForm.getIpForm().getId(), IpStatus.USED);
            updateStreetListAfterInsert(deviceForm);
            LOGGER.info("Device "+ deviceForm.getName() +" updated successfully");
        }catch(HibernateException e ){
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void gettingCorrectIDForCurrentFormAndCurrentStreet(DeviceForm deviceForm){
        if(deviceForm.getDeviceAddressForm().getStreetId() == null){
            String streetNameFromForm = deviceForm.getDeviceAddressForm().getStreet();
            Integer streetIdFromDB = streetDAO.getStreetIDByStreetName(streetNameFromForm);
            if(streetIdFromDB != null){
                deviceForm.getDeviceAddressForm().setStreetId(streetIdFromDB);
            }
        }
    }

    public void updateStreetListAfterInsert(DeviceForm form){
        Integer id = form.getDeviceAddressForm().getStreetId();
        String streetName = form.getDeviceAddressForm().getStreet();
        if(id == null && !streetName.isEmpty()){
            streetDataService.clearStreetsList();
        }
    }

    @Override
    @Transactional
    public ResponseToAjax deleteDevice(Integer id) {
        try {
            DeviceForm deviceForm = getById(id);
            ipDataService.setStatus(deviceForm.getIpForm().getId(), IpStatus.FREE);
            deviceDAO.setStatusDelete(id);
            LOGGER.info("Device "+ deviceForm.getName() +" deleted successfully");
            return ResponseToAjax.SUCCESS;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
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

}
