package com.elstele.bill.assembler;

import com.elstele.bill.dao.interfaces.DeviceTypesDAO;
import com.elstele.bill.dao.interfaces.IpDAO;
import com.elstele.bill.domain.Device;
import com.elstele.bill.form.DeviceForm;
import com.elstele.bill.form.DeviceTypesForm;
import com.elstele.bill.form.IpForm;

import static org.springframework.beans.BeanUtils.copyProperties;

public class DeviceAssembler {

    public DeviceAssembler(DeviceTypesDAO dao, IpDAO ipDao){
        setDeviceTypesDAO(dao);
        setIpDAO(ipDao);
    }
    private DeviceTypesDAO deviceTypesDAO;
    private IpDAO ipDAO;

    public DeviceForm fromBeanToForm(Device bean){
        DeviceTypesForm deviceTypesForm = new DeviceTypesForm();
        IpForm ipForm = new IpForm();
        if (bean.getDeviceTypes() != null && bean.getIpAdd() != null) {
            //bunch device with deviceTypes
            deviceTypesForm.setId(bean.getDeviceTypes().getId());
            deviceTypesForm.setDeviceType(bean.getDeviceTypes().getDeviceType());
            deviceTypesForm.setPortsNumber(bean.getDeviceTypes().getPortsNumber());
            deviceTypesForm.setDescription(bean.getDeviceTypes().getDescription());

            //bunch device with Ip
            ipForm.setId(bean.getIpAdd().getId());
            ipForm.setIpName(bean.getIpAdd().getIpName());
        }
        DeviceForm form = new DeviceForm();
        form.setDevType(deviceTypesForm);
        form.setIpForm(ipForm);
        copyProperties(bean, form);
        return form;
    }


    public Device fromFormToBean(DeviceForm form){
        Device bean = new Device();
        bean.setDeviceTypes(deviceTypesDAO.getById(form.getDevType().getId()));
        if (form.getIpForm().getId() != null) {
            bean.setIpAdd(ipDAO.getById(form.getIpForm().getId()));
        }
        copyProperties(form, bean);
        return bean;
    }

    public DeviceTypesDAO getDeviceTypesDAO() {
        return deviceTypesDAO;
    }

    public void setDeviceTypesDAO(DeviceTypesDAO deviceTypesDAO) {
        this.deviceTypesDAO = deviceTypesDAO;
    }

    public IpDAO getIpDAO() {
        return ipDAO;
    }

    public void setIpDAO(IpDAO ipDAO) {
        this.ipDAO = ipDAO;
    }
}
