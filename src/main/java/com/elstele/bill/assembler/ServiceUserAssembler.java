package com.elstele.bill.assembler;
import com.elstele.bill.domain.UserService;
import com.elstele.bill.form.ServiceUserForm;

import static org.springframework.beans.BeanUtils.copyProperties;

public class ServiceUserAssembler {
    public ServiceUserForm fromBeanToForm(UserService bean){
        ServiceUserForm form = new ServiceUserForm();
        copyProperties(bean,form);
        return form;
    }

    public UserService fromFormToBean(ServiceUserForm form){
        UserService bean = new UserService();
        copyProperties(form, bean);
        return bean;
    }

}
