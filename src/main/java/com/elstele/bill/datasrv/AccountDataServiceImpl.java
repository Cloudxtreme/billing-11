package com.elstele.bill.datasrv;


import com.elstele.bill.assembler.AccountAssembler;
import com.elstele.bill.dao.AccountDAO;
import com.elstele.bill.domain.Account;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountDataServiceImpl implements AccountDataService {

    @Autowired
    private AccountDAO accountDAO;

    @Override
    @Transactional
    public List<AccountForm> getAccountsList() {
        List<AccountForm> result = new ArrayList<AccountForm>();
        AccountAssembler assembler = new AccountAssembler();

        List<Account> beans = accountDAO.getAccountList();
        for (Account curBean : beans){
            AccountForm curForm = assembler.fromBeanToForm(curBean);
            result.add(curForm);
        }
        return result;
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

    @Override
    @Transactional
    public void updateAccount(AccountForm form) {
        AccountAssembler assembler = new AccountAssembler();
        Account account = assembler.fromFormToBean(form);
        accountDAO.update(account);
    }


    @Override
    @Transactional
    public AccountForm getAccountById(int id) {
        AccountAssembler assembler = new AccountAssembler();
        AccountForm result = null;
        Account bean = accountDAO.getById(id);
        if (bean != null){
            AccountForm form = assembler.fromBeanToForm(bean);
            result = form;
        }
        return result;
    }

    @Override
    @Transactional
    public void softDeleteAccount(int id) {
        Account account = accountDAO.getById(id);
        account.setStatus(Status.DELETED);
        accountDAO.update(account);
    }
}
