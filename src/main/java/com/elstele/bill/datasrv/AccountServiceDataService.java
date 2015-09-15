package com.elstele.bill.datasrv;

import com.elstele.bill.domain.AccountService;
import com.elstele.bill.form.AccountServiceForm;

import java.util.List;

public interface AccountServiceDataService {

/*
    public String saveService(ServiceForm form);
*/
    public String saveAccountService(AccountServiceForm form);
    public void deleteAccountService(Integer id);
    public AccountServiceForm getAccountServiceFormById(Integer id);
    public List<AccountService> listAccountServices();

}
