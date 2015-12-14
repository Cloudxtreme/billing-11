package com.elstele.bill.datasrv.impl;

import com.elstele.bill.dao.interfaces.StreetDAO;
import com.elstele.bill.datasrv.interfaces.StreetDataService;
import com.elstele.bill.domain.Street;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class StreetDataServiceImpl implements StreetDataService {
    @Autowired
    StreetDAO streetDAO;

    private static List<Street> streetsList = new ArrayList<>();
    private static List<Street> listToReturn = new ArrayList<>();

    @Override
    @Transactional
    public List<Street> getListOfStreets(String query) {
        checkIsListEmpty();
        listToReturn.clear();
        putToTheReturnList(query);
        return listToReturn;
    }

    private void checkIsListEmpty(){
        if(streetsList.isEmpty()){
            streetsList = streetDAO.getListOfStreets();
        }
    }

    private void putToTheReturnList(String query){
        for(Street street : streetsList){
            if(street.getName().contains(query)){
                listToReturn.add(street);
            }
        }
    }

}
