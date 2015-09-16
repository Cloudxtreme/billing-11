package com.elstele.bill.assembler;
import com.elstele.bill.domain.AccountService;
import com.elstele.bill.form.AccountServiceForm;

import static org.springframework.beans.BeanUtils.copyProperties;

public class AccountServiceAssembler {
    public AccountServiceForm fromBeanToForm(AccountService bean){
        AccountServiceForm form = new AccountServiceForm();
        copyProperties(bean,form);
        return form;
//    java.util.Date.getTime()/1000
    }


    public AccountService fromFormToBean(AccountServiceForm form){
        AccountService bean = new AccountService();
        copyProperties(form, bean);
        return bean;
    }

}
