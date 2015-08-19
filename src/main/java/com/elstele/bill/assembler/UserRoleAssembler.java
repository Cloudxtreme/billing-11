package com.elstele.bill.assembler;

import com.elstele.bill.domain.UserRole;
import com.elstele.bill.form.UserRoleForm;

import static org.springframework.beans.BeanUtils.copyProperties;

public class UserRoleAssembler {
    public UserRoleForm fromBeanToForm(UserRole bean){
        UserRoleForm form = new UserRoleForm();
        copyProperties(bean,form);
        return form;
    }

    public UserRole fromFormToBean(UserRoleForm form){
        UserRole bean = new UserRole();
        copyProperties(form, bean);
        return bean;
    }

}
