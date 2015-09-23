package com.elstele.bill.assembler;


import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Address;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.form.AddressForm;
import com.elstele.bill.utils.Status;

import static org.springframework.beans.BeanUtils.copyProperties;

public class AccountAssembler {

    String[] propsToSkip = {"phyAddress", "legalAddress"};

    public AccountForm fromBeanToForm(Account bean){
        AccountForm form = new AccountForm();
        copyProperties(bean, form, propsToSkip);
        form.setPhyAddress( addressAssembleFromBeanToForm(bean.getPhyAddress(), form.getPhyAddress()) );
        form.setLegalAddress( addressAssembleFromBeanToForm(bean.getLegalAddress(), form.getLegalAddress()) );
        return form;
    }

    private AddressForm addressAssembleFromBeanToForm(Address bean, AddressForm form) {
        if (bean != null){
            if (form == null){
                form = new AddressForm();
            }
            copyProperties(bean, form);
        }
        return form;
    }

    public Account fromFormToBean(AccountForm form){
        Account bean = new Account();
        copyProperties(form, bean, propsToSkip);
        bean.setPhyAddress( addressAssembleFromFormToBean(form.getPhyAddress(), bean.getPhyAddress()) );
        bean.setLegalAddress( addressAssembleFromFormToBean(form.getLegalAddress(), bean.getLegalAddress()) );

        return bean;
    }

    private Address addressAssembleFromFormToBean(AddressForm form, Address bean) {
        if (form != null){
            if (bean == null){
                bean = new Address();
                bean.setStatus(Status.ACTIVE);
                copyProperties(form, bean);
            }
        }
        return bean;
    }


}
