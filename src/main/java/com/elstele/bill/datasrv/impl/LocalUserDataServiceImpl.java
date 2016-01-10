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
        if (localUser != null) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public LocalUser findById(Integer id) {
        return localUserDAO.getById(id);
    }

    @Override
    @Transactional
    public LocalUser getUserByNameAndPass(String name, String pass) {
        LocalUser localUser = localUserDAO.getLocalUserByNameAndPass(name, pass);
        if (localUser != null) {
            return localUser;
        }
        return null;
    }

    @Override
    @Transactional
    public List<LocalUser> listLocalUser() {
        return localUserDAO.listLocalUser();
    }

    @Override
    @Transactional
    public String saveUser(LocalUserForm form) {
        LocalUserAssembler assembler = new LocalUserAssembler();
        LocalUser transientUser = assembler.fromFormToBean(form);
        for (int roleId : form.getRoleId()) {
            transientUser.addUserRole(userRoleDAO.getById(roleId));
        }
        if (form.isNew()) {
            transientUser.setStatus(Status.ACTIVE);
            localUserDAO.create(transientUser);
            return "user.success.add";
        } else {
            localUserDAO.update(transientUser);
            return "user.success.update";
        }
    }

    @Override
    @Transactional
    public boolean checkUniqueUserName(LocalUserForm form) {
        LocalUser user = localUserDAO.getByName(form.getUsername());
        return user == null || user.getId().equals(form.getId()) && user.getUsername().equals(form.getUsername());
    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        localUserDAO.setStatusDelete(id);
    }

    @Override
    @Transactional
    public LocalUserForm getLocalUserFormById(Integer id) {
        LocalUserAssembler assembler = new LocalUserAssembler();
        LocalUserForm result = null;
        LocalUser bean = localUserDAO.getById(id);
        if (bean != null) {
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
