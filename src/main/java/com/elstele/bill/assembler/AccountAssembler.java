package com.elstele.bill.assembler;


import com.elstele.bill.domain.*;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.form.AddressForm;
import com.elstele.bill.form.ServiceForm;
import com.elstele.bill.utils.Status;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.springframework.beans.BeanUtils.copyProperties;

public class AccountAssembler {

    String[] accountPropsToSkip = {"phyAddress", "legalAddress","serviceForms"};
    String[] addressPropsToSkip = {"street", "streetId"};

    public AccountForm fromBeanToForm(Account bean){
        AccountForm form = new AccountForm();
        copyProperties(bean, form, accountPropsToSkip);

        form.setPhyAddress( addressAssembleFromBeanToForm(bean.getPhyAddress(), form.getPhyAddress()) );
        form.setLegalAddress( addressAssembleFromBeanToForm(bean.getLegalAddress(), form.getLegalAddress()) );

        form.setServiceForms( serviceAssembleFromBeanToForm(bean.getAccountServices(), form.getServiceForms()) );
        return form;
    }

    private AddressForm addressAssembleFromBeanToForm(Address bean, AddressForm form) {
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
        bean.setPhyAddress( addressAssembleFromFormToBean(form.getPhyAddress(), bean.getPhyAddress()) );
        bean.setLegalAddress( addressAssembleFromFormToBean(form.getLegalAddress(), bean.getLegalAddress()) );

        return bean;
    }

    private Address addressAssembleFromFormToBean(AddressForm form, Address bean) {
        if (form != null){
            if (isAddressEmpty(form)){
                return null;
            }
            if (bean == null){
                bean = new Address();
                bean.setStatus(Status.ACTIVE);
                copyProperties(form, bean, addressPropsToSkip);
                if (form.getStreetId() != null){
                    Street str = new Street(form.getStreetId(), form.getStreet());
                    bean.setStreet(str);
                }
            }
        }
        return bean;
    }

    private boolean isAddressEmpty(AddressForm form) {
        if (form != null){
            if(form.getId() != null || form.getStreetId() != null){
                return false;
            }
        }
        return true;
    }


}
