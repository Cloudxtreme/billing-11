package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.AccountService;

import java.util.List;

public interface AccountServiceDAO extends CommonDAO <AccountService> {
    public List<AccountService> listAccountServices();
}
