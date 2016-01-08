package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.DeviceTypesAssembler;
import com.elstele.bill.dao.interfaces.DeviceTypesDAO;
import com.elstele.bill.datasrv.interfaces.DeviceTypesDataService;
import com.elstele.bill.domain.DeviceTypes;
import com.elstele.bill.form.DeviceTypesForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DeviceTypesDataServiceImpl implements DeviceTypesDataService {
    @Autowired
    DeviceTypesDAO deviceTypesDAO;

    @Override
    @Transactional
    public List<DeviceTypesForm> getDeviceTypes() {
        List<DeviceTypesForm> result = new ArrayList<DeviceTypesForm>();
        DeviceTypesAssembler assembler = new DeviceTypesAssembler();

        List<DeviceTypes> beans = deviceTypesDAO.getDeviceTypes();
        for (DeviceTypes curBean : beans) {
            DeviceTypesForm curForm = assembler.fromBeanToForm(curBean);
            result.add(curForm);
        }
        return result;
    }

    @Override
    @Transactional
    public HashMap<Integer, String> getDeviceTypesAsMap() {
        List<DeviceTypesForm> devType = getDeviceTypes();
        HashMap<Integer, String> map = new HashMap<>();
        for (DeviceTypesForm deviceTypesForm : devType)
            map.put(deviceTypesForm.getId(), deviceTypesForm.getDeviceType());
        return map;
    }

    @Override
    @Transactional
    public Integer addDeviceType(DeviceTypesForm deviceTypesForm){
        DeviceTypesAssembler deviceTypesAssembler = new DeviceTypesAssembler();
        DeviceTypes deviceTypes = deviceTypesAssembler.fromFormToBean(deviceTypesForm);
        return deviceTypesDAO.create(deviceTypes);
    }

    @Override
    @Transactional
    public DeviceTypesForm getById(Integer id){
        DeviceTypesAssembler assembler = new DeviceTypesAssembler();
        DeviceTypes bean = deviceTypesDAO.getById(id);
        DeviceTypesForm result = assembler.fromBeanToForm(bean);
        return result;
    }

    @Override
    @Transactional
    public void deleteDeviceType(Integer id){
        deviceTypesDAO.delete(id);
    }

    @Override
    @Transactional
    public void updateDeviceTypes(DeviceTypesForm deviceTypesForm){
        DeviceTypesAssembler assembler = new DeviceTypesAssembler();
        DeviceTypes bean = assembler.fromFormToBean(deviceTypesForm);
        deviceTypesDAO.update(bean);
    }
}
