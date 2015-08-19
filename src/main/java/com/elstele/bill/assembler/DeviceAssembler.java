package com.elstele.bill.assembler;

import com.elstele.bill.dao.DeviceTypesDAO;
import com.elstele.bill.domain.Device;
import com.elstele.bill.form.DeviceForm;
import com.elstele.bill.form.DeviceTypesForm;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.beans.BeanUtils.copyProperties;

public class DeviceAssembler {

    public DeviceAssembler(DeviceTypesDAO dao){
        setDeviceTypesDAO(dao);
    }
    private DeviceTypesDAO deviceTypesDAO;


    public DeviceForm fromBeanToForm(Device bean){
        DeviceTypesForm deviceTypesForm = new DeviceTypesForm();
        if (bean.getDeviceTypes() != null) {
            deviceTypesForm.setId(bean.getDeviceTypes().getId());
            deviceTypesForm.setDeviceType(bean.getDeviceTypes().getDeviceType());
        }
        DeviceForm form = new DeviceForm();
        form.setDevType(deviceTypesForm);
        copyProperties(bean,form);
        return form;
    }


    public Device fromFormToBean(DeviceForm form){
        Integer devTypeId = form.getDevType().getId();
        Device bean = new Device();
        bean.setDeviceTypes(deviceTypesDAO.getById(devTypeId));
        copyProperties(form, bean);
        return bean;
    }

    public DeviceTypesDAO getDeviceTypesDAO() {
        return deviceTypesDAO;
    }

    public void setDeviceTypesDAO(DeviceTypesDAO deviceTypesDAO) {
        this.deviceTypesDAO = deviceTypesDAO;
    }
}
