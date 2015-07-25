package com.elstele.bill.datasrv;

import com.elstele.bill.dao.UserActivityDAO;
import com.elstele.bill.dao.UserRoleDAO;
import com.elstele.bill.domain.Activity;
import com.elstele.bill.domain.UserRole;
import com.elstele.bill.form.UserRoleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Service
public class UserRoleDataServiceImpl implements UserRoleDataService {

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Autowired
    private UserActivityDAO userActivityDAO;

    @Override
    @Transactional
    public void saveRole(UserRoleForm form){
        UserRole role = new UserRole();
        role.setName(form.getName());
        role.setDescription(form.getDescription());
        for (int activityId : form.getActivityId()) {
            role.addActivity(userActivityDAO.getActivityById(activityId));
        }
        userRoleDAO.insertUserRole(role);
    }

    @Override
    @Transactional
    public void deleteRole(Integer id){
        userRoleDAO.delete(id);
    }

    @Override
    @Transactional
    public List<UserRole> listUserRole(){
        return userRoleDAO.listUserRole();
    }

    @Override
    @Transactional
    public UserRole findById(Integer id){
        return userRoleDAO.getById(id);
    }


}
