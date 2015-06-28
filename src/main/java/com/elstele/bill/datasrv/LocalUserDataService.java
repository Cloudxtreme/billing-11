package com.elstele.bill.datasrv;


import com.elstele.bill.domain.LocalUser;

public interface LocalUserDataService {

    public boolean isCredentialValid(String name, String pass);
    public LocalUser getUserByNameAndPass(String name, String pass);

}
