package com.elstele.bill.assembler;

import com.elstele.bill.domain.Device;
import com.elstele.bill.form.DeviceForm;
import static org.springframework.beans.BeanUtils.copyProperties;

public class DeviceAssembler {
    public DeviceForm fromBeanToForm(Device bean){
        DeviceForm form = new DeviceForm();

        copyProperties(bean,form);
        return form;
    }

    public Device fromFormToBean(DeviceForm form){
        Device bean = new Device();
        copyProperties(form, bean);
        return bean;
    }

}
