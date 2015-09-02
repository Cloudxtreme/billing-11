package com.elstele.bill.datasrv;


import com.elstele.bill.assembler.AccountAssembler;
import com.elstele.bill.assembler.LocalUserAssembler;
import com.elstele.bill.dao.LocalUserDAO;
import com.elstele.bill.dao.UserRoleDAO;
import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.domain.UserRole;
import com.elstele.bill.form.LocalUserForm;
import com.elstele.bill.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EnumType;
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
        LocalUser user = assembler.fromFormToBean(form);
        String message = "Local User was successfully ";
        for (int roleId : form.getRoleId()) {
            user.addUserRole(userRoleDAO.getById(roleId));
        }

        if(form.isNew()){
            localUserDAO.create(user);
            message += "added.";
        }
        else{
            localUserDAO.update(user);
            message += "updated.";
        }
        return message;
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
