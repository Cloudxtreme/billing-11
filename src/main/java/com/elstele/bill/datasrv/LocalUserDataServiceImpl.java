package com.elstele.bill.datasrv;


import org.springframework.stereotype.Service;

@Service
public class LocalUserDataServiceImpl implements LocalUserDataService {

    @Override
    public boolean isCredentialValid(String name, String pass) {
        return false;
    }
}
