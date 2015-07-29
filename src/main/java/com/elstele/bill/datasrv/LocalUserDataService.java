package com.elstele.bill.datasrv;


import com.elstele.bill.domain.LocalUser;

import java.util.List;

public interface LocalUserDataService {

    public boolean isCredentialValid(String name, String pass);
    public LocalUser getUserByNameAndPass(String name, String pass);
    public List<LocalUser> listLocalUser();

}
