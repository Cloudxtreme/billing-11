package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.domain.UserRole;
import com.elstele.bill.form.UserRoleForm;

import java.util.List;

public interface UserRoleDataService {

    public String saveRole(UserRoleForm form);
    public UserRole findById(Integer id);
    public void deleteRole(Integer id);
    public List<UserRoleForm> listUserRole();
    public UserRoleForm getUserRoleFormById(Integer id);
    public boolean checkUniqueRoleName(UserRoleForm form);
}
