package com.elstele.bill.datasrv;


import com.elstele.bill.dao.LocalUserDAO;
import com.elstele.bill.domain.LocalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocalUserDataServiceImpl implements LocalUserDataService {

    @Autowired
    private LocalUserDAO localUserDAO;

    @Override
    @Transactional
    public boolean isCredentialValid(String name, String pass) {
        LocalUser localUser = localUserDAO.getLocalUserByNameAndPass(name, pass);
        if (localUser != null){
            return true;
        }
        return false;
    }
}
