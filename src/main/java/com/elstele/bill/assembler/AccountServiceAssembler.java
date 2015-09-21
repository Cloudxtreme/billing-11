package com.elstele.bill.assembler;
import com.elstele.bill.domain.Account;
import com.elstele.bill.assembler.AccountAssembler;
import com.elstele.bill.domain.AccountService;
import com.elstele.bill.domain.ServiceT;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.form.AccountServiceForm;
import com.elstele.bill.form.ServiceForm;
import com.elstele.bill.utils.Status;

import static org.springframework.beans.BeanUtils.copyProperties;

public class AccountServiceAssembler  extends AccountAssembler {

    String[] propsToSkipArr = {"service", "account"};

    public AccountServiceForm fromBeanToFormAccountService(AccountService bean){
        AccountServiceForm form = new AccountServiceForm();
        copyProperties(bean,form,propsToSkipArr);
        form.setAccount(fromBeanToForm(bean.getAccount()));
        form.setService( serviceAssembleFromBeanToForm(bean.getService(), form.getService()) );
        return form;
    }

    private ServiceForm serviceAssembleFromBeanToForm(ServiceT bean, ServiceForm form) {
        if (bean != null){
            if (form == null){
                form = new ServiceForm();
            }
            copyProperties(bean, form);
        }
        return form;
    }

// ------------------------------------

    public AccountService fromFormToBeanAccountService(AccountServiceForm form){
        AccountService bean = new AccountService();
        copyProperties(form, bean, propsToSkipArr);
        bean.setAccount(fromFormToBean(form.getAccount()));
        bean.setService(serviceAssembleFromFormToBean(form.getService(), bean.getService()));
        return bean;
    }

    private ServiceT serviceAssembleFromFormToBean(ServiceForm form, ServiceT bean) {
        if (form != null){
            if (bean == null){
                bean = new ServiceT();
                bean.setStatus(Status.ACTIVE);
                copyProperties(form, bean);
            }
        }
        return bean;
    }
}
