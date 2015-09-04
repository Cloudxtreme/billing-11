package com.elstele.bill.datasrv;

import com.elstele.bill.assembler.DeviceTypesAssembler;
import com.elstele.bill.dao.DeviceTypesDAO;
import com.elstele.bill.domain.DeviceTypes;
import com.elstele.bill.form.DeviceTypesForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    public void addDeviceType(DeviceTypesForm deviceTypesForm){
        DeviceTypesAssembler deviceTypesAssembler = new DeviceTypesAssembler();
        DeviceTypes deviceTypes = deviceTypesAssembler.fromFormToBean(deviceTypesForm);
        /*deviceTypes.setDeviceType(deviceTypesForm.getDeviceType());
        deviceTypes.setDescription(deviceTypesForm.getDescription());
        deviceTypes.setPortsNubmer(deviceTypesForm.getPortsNumber());*/
        deviceTypesDAO.create(deviceTypes);
    }
}
