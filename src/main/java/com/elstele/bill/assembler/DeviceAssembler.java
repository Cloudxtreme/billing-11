package com.elstele.bill.assembler;

import com.elstele.bill.dao.interfaces.DeviceTypesDAO;
import com.elstele.bill.dao.interfaces.IpDAO;
import com.elstele.bill.domain.Device;
import com.elstele.bill.form.AddressForm;
import com.elstele.bill.form.DeviceForm;
import com.elstele.bill.form.DeviceTypesForm;
import com.elstele.bill.form.IpForm;

import static org.springframework.beans.BeanUtils.copyProperties;

public class DeviceAssembler {

    public DeviceAssembler(DeviceTypesDAO dao, IpDAO ipDao) {
        setDeviceTypesDAO(dao);
        setIpDAO(ipDao);
    }

    private DeviceTypesDAO deviceTypesDAO;
    private IpDAO ipDAO;

    public DeviceForm fromBeanToForm(Device bean) {
        DeviceTypesForm deviceTypesForm = new DeviceTypesForm();
        IpForm ipForm = new IpForm();
        AddressForm addressForm = new AddressForm();
        if (bean.getDeviceType() != null) {
            //bunch device with deviceTypes
            deviceTypesForm.setId(bean.getDeviceType().getId());
            deviceTypesForm.setDeviceType(bean.getDeviceType().getDeviceType());
            deviceTypesForm.setPortsNumber(bean.getDeviceType().getPortsNumber());
            deviceTypesForm.setDescription(bean.getDeviceType().getDescription());
        }
        if (bean.getIpAdd() != null) {
            //bunch device with Ip
            ipForm.setId(bean.getIpAdd().getId());
            ipForm.setIpName(bean.getIpAdd().getIpName());
        }
        if (bean.getDeviceAddress() != null) {
            //bunch device with addresses
            addressForm.setId(bean.getDeviceAddress().getId());
            addressForm.setBuilding(bean.getDeviceAddress().getBuilding());
            addressForm.setFlat(bean.getDeviceAddress().getFlat());
            addressForm.setStreet(bean.getDeviceAddress().getStreet().getName());
        }
        DeviceForm form = new DeviceForm();
        form.setDevType(deviceTypesForm);
        form.setIpForm(ipForm);
        form.setDeviceAddressForm(addressForm);
        copyProperties(bean, form);
        return form;
    }


    public Device fromFormToBean(DeviceForm form) {
        Device bean = new Device();
        if (form.getDevType() != null) {
            bean.setDeviceType(deviceTypesDAO.getById(form.getDevType().getId()));
        }
        if (form.getIpForm() != null && form.getIpForm().getId() != null) {
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
