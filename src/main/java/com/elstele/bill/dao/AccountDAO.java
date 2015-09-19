package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.Account;

import java.util.List;


public interface AccountDAO extends CommonDAO<Account>{

    public List<Account> getAccountList(int limit, int offset);
    public List<Account> getAccountList();
    public Integer getActiveAccountsCount();
}
