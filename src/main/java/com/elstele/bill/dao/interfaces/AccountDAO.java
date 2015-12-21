package com.elstele.bill.dao.interfaces;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Street;

import java.util.List;


public interface AccountDAO extends CommonDAO<Account>{

    public List<Account> getAccountList(int limit, int offset);
    public List<Account> getAccountList();
    public Integer getActiveAccountsCount();
    public List<Account> searchAccounts(String value);
}
