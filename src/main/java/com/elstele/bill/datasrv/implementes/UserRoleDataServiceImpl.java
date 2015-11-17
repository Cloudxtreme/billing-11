package com.elstele.bill.datasrv.implementes;

import com.elstele.bill.assembler.UserRoleAssembler;
import com.elstele.bill.dao.interfaces.UserActivityDAO;
import com.elstele.bill.dao.interfaces.UserRoleDAO;
import com.elstele.bill.datasrv.interfaces.UserRoleDataService;
import com.elstele.bill.domain.Activity;
import com.elstele.bill.domain.UserRole;
import com.elstele.bill.form.UserRoleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserRoleDataServiceImpl implements UserRoleDataService {

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Autowired
    private UserActivityDAO userActivityDAO;

    @Override
    @Transactional
    public String saveRole(UserRoleForm form){
        UserRoleAssembler assembler = new UserRoleAssembler();
        UserRole role = assembler.fromFormToBean(form);
        String message = "User Role was successfully ";
        for (int roleId : form.getActivityId()) {
            role.addActivity(userActivityDAO.getById(roleId));
        }
        if(form.isNew()){
            userRoleDAO.create(role);
            message += "added.";
        }
        else{
            userRoleDAO.update(role);
            message += "updated.";
        }
        return message;
    }

    @Override
    @Transactional
    public void deleteRole(Integer id){
        userRoleDAO.setStatusDelete(id);
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

    @Override
    @Transactional
    public UserRoleForm getUserRoleFormById(Integer id){
        UserRoleAssembler assembler = new UserRoleAssembler();
        UserRoleForm result = null;
        UserRole bean = userRoleDAO.getById(id);
        if (bean != null){
            UserRoleForm form = assembler.fromBeanToForm(bean);
            ArrayList<Integer> activityList = new ArrayList<Integer>();
            for (Activity activity : bean.getActivities()) {
                activityList.add(activity.getId());
            }
            form.setActivityId(activityList);
            result = form;
        }
        return result;
    }

}
