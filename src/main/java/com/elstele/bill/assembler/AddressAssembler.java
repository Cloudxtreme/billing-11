package com.elstele.bill.assembler;

import com.elstele.bill.domain.Address;
import com.elstele.bill.domain.Street;
import com.elstele.bill.form.AddressForm;
import com.elstele.bill.utils.Enums.Status;

import static org.springframework.beans.BeanUtils.copyProperties;

public class AddressAssembler {
    String[] addressPropsToSkip = {"street", "streetId"};

    public AddressForm addressAssembleFromBeanToForm(Address bean, AddressForm form) {
        if (bean != null){
            if (form == null){
                form = new AddressForm();
            }
            copyProperties(bean, form, addressPropsToSkip);
            if (bean.getStreet() != null){
                form.setStreet(bean.getStreet().getName());
                form.setStreetId(bean.getStreet().getId());
            }
        }
        return form;
    }

    public Address addressAssembleFromFormToBean(AddressForm form, Address bean) {
        if (form != null){
            if (bean == null){
                bean = new Address();
                bean.setStatus(Status.ACTIVE);
                copyProperties(form, bean, addressPropsToSkip);
                if (form.getStreetId() != null){
                    Street str = new Street(form.getStreetId(), form.getStreet());
                    bean.setStreet(str);
                }
                if(form.getStreetId() == null && form.getStreet()!= null && !form.getStreet().equals("")){
                    Street str = new Street();
                    str.setName(form.getStreet());
                    bean.setStreet(str);
                }
            }
        }
        return bean;
    }
}
