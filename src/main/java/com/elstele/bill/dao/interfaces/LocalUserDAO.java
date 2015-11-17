package com.elstele.bill.dao.interfaces;


import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.LocalUser;

import java.util.List;

public interface LocalUserDAO extends CommonDAO<LocalUser> {

    public LocalUser getLocalUserByNameAndPass(String name, String pass);
    public List<LocalUser> listLocalUser();

}
