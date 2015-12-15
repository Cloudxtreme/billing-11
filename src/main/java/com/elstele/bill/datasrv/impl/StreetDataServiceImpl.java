package com.elstele.bill.datasrv.impl;

import com.elstele.bill.dao.interfaces.StreetDAO;
import com.elstele.bill.datasrv.interfaces.StreetDataService;
import com.elstele.bill.domain.Street;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class StreetDataServiceImpl implements StreetDataService {
    @Autowired
    StreetDAO streetDAO;

    private List<Street> streetsList = Collections.emptyList();

    @Override
    @Transactional
    public List<Street> getListOfStreets(String query) {
        checkIsListEmpty();
        return putToTheReturnList(query);
    }

    private void checkIsListEmpty(){
        if(streetsList.isEmpty()){
            streetsList = streetDAO.getListOfStreets();
        }
    }

    @Override
    public void clearStreetsList(){
        streetsList.clear();
    }

    private List<Street> putToTheReturnList(String query){
        List<Street> listToReturn = new ArrayList<>();
        for(Street street : streetsList){
            if(street.getName().toLowerCase().contains(query.toLowerCase())){
                listToReturn.add(street);
            }
        }
        return listToReturn;
    }

}
