package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.UserService;

import java.util.List;

public interface ServiceUserDAO extends CommonDAO <UserService> {
    public List<UserService> listUserServices();
}
