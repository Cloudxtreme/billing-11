package com.elstele.bill.assembler;



import com.elstele.bill.domain.DeviceTypes;

import com.elstele.bill.form.DeviceForm;
import com.elstele.bill.form.DeviceTypesForm;
import static org.springframework.beans.BeanUtils.copyProperties;

public class DeviceTypesAssembler {

    public DeviceTypesForm fromBeanToForm(DeviceTypes bean){

        DeviceTypesForm form = new DeviceTypesForm();
        copyProperties(bean,form);
        return form;
    }

    public DeviceTypes fromFormToBean(DeviceTypesForm form){
        DeviceTypes bean = new DeviceTypes();
        copyProperties(form, bean);
        return bean;
    }
}
