package com.elstele.bill.datasrv;

import com.elstele.bill.domain.AccountService;
import com.elstele.bill.form.AccountServiceForm;

import java.util.List;

public interface AccountServiceDataService {

/*
    public String saveService(ServiceForm form);
    public void deleteService(Integer id);
*/
    public String saveAccountService(AccountServiceForm form);
    public AccountServiceForm getAccountServiceFormById(Integer id);
    public List<AccountService> listAccountServices();

}
