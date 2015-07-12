package com.elstele.bill.datasrv;


import com.elstele.bill.assembler.AccountAssembler;
import com.elstele.bill.dao.AccountDAO;
import com.elstele.bill.domain.Account;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountDataServiceImpl implements AccountDataService {

    @Autowired
    private AccountDAO accountDAO;

    @Override
    public List<AccountForm> getAccountsList() {
        return null;
    }

    @Override
    @Transactional
    public void saveAccount(AccountForm form) {
        AccountAssembler assembler = new AccountAssembler();
        if (form.getId() == null){
            form.setCurrentBalance(0F);
            form.setStatus(Status.ACTIVE);
        }
        Account account = assembler.fromFormToBean(form);
        accountDAO.create(account);
    }
}
