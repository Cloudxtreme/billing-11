package com.elstele.bill.dao;


import com.elstele.bill.domain.LocalUser;

public interface LocalUserDAO extends CommonDAO <LocalUser> {
    public LocalUser getLocalUserByNameAndPass(String name, String pass);
}
