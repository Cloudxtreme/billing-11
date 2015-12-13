package com.elstele.bill.assembler;


import com.elstele.bill.domain.*;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.form.AddressForm;
import com.elstele.bill.form.ServiceForm;
import com.elstele.bill.utils.Enums.Status;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.springframework.beans.BeanUtils.copyProperties;

public class AccountAssembler {

    String[] accountPropsToSkip = {"phyAddress", "legalAddress","serviceForms"};

    public AccountForm fromBeanToForm(Account bean){
        AccountForm form = new AccountForm();
        copyProperties(bean, form, accountPropsToSkip);
        AddressAssembler addressAssembler = new AddressAssembler();

        form.setPhyAddress( addressAssembler.addressAssembleFromBeanToForm(bean.getPhyAddress(), form.getPhyAddress()) );
        form.setLegalAddress( addressAssembler.addressAssembleFromBeanToForm(bean.getLegalAddress(), form.getLegalAddress()) );

        form.setServiceForms( serviceAssembleFromBeanToForm(bean.getAccountServices(), form.getServiceForms()) );
        return form;
    }

    private List<ServiceForm> serviceAssembleFromBeanToForm(Set<Service> beans, List<ServiceForm> serviceForms) {
        serviceForms = new ArrayList<ServiceForm>();
        if(beans!=null) {
            ServiceAssembler servAssembler = new ServiceAssembler();
            Iterator iterator = beans.iterator();
            while (iterator.hasNext()) {
                Service bean = (Service) iterator.next();
                ServiceForm form;
                if (bean instanceof ServiceInternet) {
                    form = servAssembler.fromInternetBeanToForm((ServiceInternet) bean);
                } else if (bean instanceof ServicePhone) {
                    form = servAssembler.fromPhoneBeanToForm((ServicePhone) bean);
                } else {
                    form = servAssembler.fromServiceBeanToForm(bean);
                }
                serviceForms.add(form);
            }
        }
        return serviceForms;
    }

    public Account fromFormToBean(AccountForm form){
        Account bean = new Account();
        copyProperties(form, bean, accountPropsToSkip);
        AddressAssembler addressAssembler = new AddressAssembler();
        bean.setPhyAddress( addressAssembler.addressAssembleFromFormToBean(form.getPhyAddress(), bean.getPhyAddress()) );
        bean.setLegalAddress( addressAssembler.addressAssembleFromFormToBean(form.getLegalAddress(), bean.getLegalAddress()) );

        Integer phyStreetId = form.getPhyAddress().getStreetId();
        Integer legalStreetId = form.getLegalAddress().getStreetId();
        if (phyStreetId.equals(legalStreetId)){
            Street sameStreet = bean.getPhyAddress().getStreet();
            bean.getLegalAddress().setStreet(sameStreet);
        }

        return bean;
    }

    public AccountForm fromBeanToShortForm(Account bean) {
        AccountForm form = new AccountForm();
        copyProperties(bean, form, accountPropsToSkip);
        return form;
    }
}
