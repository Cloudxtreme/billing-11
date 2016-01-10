package com.elstele.bill.datasrv.interfaces;


import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.form.LocalUserForm;

import java.util.List;

public interface LocalUserDataService {

    public boolean isCredentialValid(String name, String pass);
    public LocalUser findById(Integer id);
    public LocalUser getUserByNameAndPass(String name, String pass);
    public String saveUser(LocalUserForm form);
    public List<LocalUser> listLocalUser();
    public void deleteUser(Integer id);
    public LocalUserForm getLocalUserFormById(Integer id);
    public boolean checkUniqueUserName(LocalUserForm form);

}
