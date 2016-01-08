package com.elstele.bill.datasrv.impl;


import com.elstele.bill.assembler.LocalUserAssembler;
import com.elstele.bill.dao.interfaces.LocalUserDAO;
import com.elstele.bill.dao.interfaces.UserRoleDAO;
import com.elstele.bill.datasrv.interfaces.LocalUserDataService;
import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.domain.UserRole;
import com.elstele.bill.form.LocalUserForm;
import com.elstele.bill.utils.Enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocalUserDataServiceImpl implements LocalUserDataService {

    @Autowired
    private LocalUserDAO localUserDAO;

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Override
    @Transactional
    public boolean isCredentialValid(String name, String pass) {
        LocalUser localUser = localUserDAO.getLocalUserByNameAndPass(name, pass);
        if (localUser != null){
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public LocalUser findById(Integer id){
        return localUserDAO.getById(id);
    }

    @Override
    @Transactional
    public LocalUser getUserByNameAndPass(String name, String pass) {
        LocalUser localUser = localUserDAO.getLocalUserByNameAndPass(name, pass);
        if (localUser != null){
            return localUser;
        }
        return null;
    }

    @Override
    @Transactional
    public List<LocalUser> listLocalUser(){
        return localUserDAO.listLocalUser();
    }

    @Override
    @Transactional
    public String saveUser(LocalUserForm form){
        LocalUserAssembler assembler = new LocalUserAssembler();
        LocalUser persistentUser = assembler.fromFormToBean(form);
        for (int roleId : form.getRoleId()) {
            persistentUser.addUserRole(userRoleDAO.getById(roleId));
        }
        LocalUser transientUser = localUserDAO.getByName(form.getUsername());
        if(form.isNew()){
            return create(transientUser, persistentUser);
        }
        else{
            return update(transientUser, persistentUser);
        }
    }

    private String create(LocalUser transientUser, LocalUser persistentUser) {
        if (transientUser == null) {
            return createNew(persistentUser);
        }
        return checkTryRestoreDeleted(transientUser);
    }

    private String createNew(LocalUser persistentUser){
        persistentUser.setStatus(Status.ACTIVE);
        localUserDAO.create(persistentUser);
        return "user.success.add";
    }

    private String checkTryRestoreDeleted(LocalUser transientUser){
        if (transientUser.getStatus() == Status.DELETED) {
            return "user.error.restored";
        } else {
            return "user.error.create";
        }
    }

    private String update(LocalUser transientUser, LocalUser persistentUser){
        if(transientUser != null) {
            return updateTransient(transientUser, persistentUser);
        }else{
            localUserDAO.update(persistentUser);
            return "user.success.update";
        }
    }

    private String updateTransient(LocalUser transientUser, LocalUser persistentUser){
        if (transientUser.getId().equals(persistentUser.getId()) && transientUser.getUsername().equals(persistentUser.getUsername())) {
            localUserDAO.update(transientUser);
            return "user.success.update";
        } else {
            return "user.error.update";
        }
    }

    @Override
    @Transactional
    public void deleteUser(Integer id){
        localUserDAO.setStatusDelete(id);
    }

    @Override
    @Transactional
    public LocalUserForm getLocalUserFormById(Integer id) {
        LocalUserAssembler assembler = new LocalUserAssembler();
        LocalUserForm result = null;
        LocalUser bean = localUserDAO.getById(id);
        if (bean != null){
            LocalUserForm form = assembler.fromBeanToForm(bean);
            ArrayList<Integer> roleList = new ArrayList<Integer>();
            for (UserRole userRole : bean.getUserRoles()) {
                roleList.add(userRole.getId());
            }
            form.setRoleId(roleList);
            result = form;
        }
        return result;
    }


}
