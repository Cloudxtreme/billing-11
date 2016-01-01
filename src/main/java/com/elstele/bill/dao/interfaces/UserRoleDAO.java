package com.elstele.bill.dao.interfaces;


import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.Activity;
import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.domain.UserRole;

import java.util.List;

public interface UserRoleDAO extends CommonDAO <UserRole> {
    public List<UserRole> listUserRole();
    public UserRole getByName(String name);
}
