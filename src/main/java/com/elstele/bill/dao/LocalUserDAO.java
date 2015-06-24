package com.elstele.bill.dao;


import com.elstele.bill.domain.LocalUser;

public interface LocalUserDAO {
    public LocalUser getLocalUserById(Integer id);
    public LocalUser getLocalUserByNameAndPass(String name, String pass);
    public void save(LocalUser user);
    public void update(LocalUser user);
    public void delete(Integer id);
}
