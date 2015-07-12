package com.elstele.bill.assembler;


import com.elstele.bill.domain.Account;
import com.elstele.bill.form.AccountForm;
import static org.springframework.beans.BeanUtils.copyProperties;

public class AccountAssembler {

    public AccountForm fromBeanToForm(Account bean){
        AccountForm form = new AccountForm();

        copyProperties(bean, form);
        return form;
    }

    public Account fromFormToBean(AccountForm form){
        Account bean = new Account();
        copyProperties(form, bean);
        return bean;
    }


}
