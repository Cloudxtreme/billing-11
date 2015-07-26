package com.elstele.bill.datasrv;

import com.elstele.bill.domain.UserRole;
import com.elstele.bill.form.UserRoleForm;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface UserRoleDataService {

    public void saveRole(UserRoleForm form);
    public UserRole findById(Integer id);
    public ModelAndView editRole(UserRoleForm form);
    public ModelAndView deleteRole(UserRoleForm form);
    public List<UserRole> listUserRole();
}
