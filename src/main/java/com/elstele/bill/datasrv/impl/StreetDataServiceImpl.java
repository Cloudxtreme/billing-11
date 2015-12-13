package com.elstele.bill.datasrv.impl;

import com.elstele.bill.dao.interfaces.StreetDAO;
import com.elstele.bill.datasrv.interfaces.StreetDataService;
import com.elstele.bill.domain.Street;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StreetDataServiceImpl implements StreetDataService {
    @Autowired
    StreetDAO streetDAO;

    @Override
    @Transactional
    public List<Street> getListOfStreets(String query) {
        return streetDAO.getListOfStreets(query);
    }
}
