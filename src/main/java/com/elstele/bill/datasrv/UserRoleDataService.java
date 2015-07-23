package com.elstele.bill.datasrv;

import com.elstele.bill.domain.Activity;
import com.elstele.bill.domain.UserRole;
import com.elstele.bill.form.ActivityForm;
import com.elstele.bill.form.UserPanelForm;
import com.elstele.bill.form.UserRoleForm;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface UserRoleDataService {

    public String isValid(String name, String description);
    public void saveRole(UserRoleForm form);
    public ModelAndView editRole(UserRoleForm form);
    public ModelAndView deleteRole(UserRoleForm form);
    public List<UserRole> listUserRole();
}
