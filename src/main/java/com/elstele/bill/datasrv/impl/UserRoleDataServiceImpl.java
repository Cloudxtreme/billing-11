package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.UserRoleAssembler;
import com.elstele.bill.dao.interfaces.UserActivityDAO;
import com.elstele.bill.dao.interfaces.UserRoleDAO;
import com.elstele.bill.datasrv.interfaces.UserRoleDataService;
import com.elstele.bill.domain.UserRole;
import com.elstele.bill.form.UserRoleForm;
import com.elstele.bill.utils.Enums.Status;
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
        UserRole persistentRole = assembler.fromFormToBean(form);
        UserRole transientRole = userRoleDAO.getByName(form.getName());
        if(form.isNew()){
            return create(transientRole, persistentRole);
        }
        else{
            return update(transientRole, persistentRole);
        }
    }

    private String create(UserRole transientRole, UserRole persistentRole) {
        if (transientRole == null) {
            return createNew(persistentRole);
        }
        return checkTryRestoreDeleted(transientRole);
    }

    private String createNew(UserRole persistentRole){
        persistentRole.setStatus(Status.ACTIVE);
        userRoleDAO.create(persistentRole);
        return "userrole.success.add";
    }

    private String checkTryRestoreDeleted(UserRole transientRole){
        if (transientRole.getStatus() == Status.DELETED) {
            return "userrole.error.restored";
        } else {
            return "userrole.error.create";
        }
    }

    private String update(UserRole transientRole, UserRole persistentRole){
        if(transientRole != null) {
            return updateTransient(transientRole, persistentRole);
        }else{
            userRoleDAO.update(persistentRole);
            return "userrole.success.update";
        }
    }

    private String updateTransient(UserRole transientRole, UserRole persistentRole){
        if (transientRole.getId().equals(persistentRole.getId()) && transientRole.getName().equals(persistentRole.getName())) {
            userRoleDAO.update(transientRole);
            return "userrole.success.update";
        } else {
            return "userrole.error.update";
        }
    }

    @Override
    @Transactional
    public void deleteRole(Integer id){
        userRoleDAO.setStatusDelete(id);
    }

    @Override
    @Transactional
    public List<UserRoleForm> listUserRole(){
        List<UserRoleForm> result = new ArrayList<UserRoleForm>();
        UserRoleAssembler assembler = new UserRoleAssembler();

        List<UserRole> beans = userRoleDAO.listUserRole();
        if(beans != null) {
            for (UserRole curBean : beans) {
/*
                UserRoleForm curForm = assembler.fromBeanToForm(curBean);
                ArrayList<Integer> activityList = new ArrayList<Integer>();
                for (Activity activity : curBean.getActivities()) {
                    activityList.add(activity.getId());
                }
                curForm.setActivityId(activityList);
*/

                result.add(assembler.fromBeanToForm(curBean));
            }
        }
        return result;
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
            result = form;
        }
        return result;
    }

}
