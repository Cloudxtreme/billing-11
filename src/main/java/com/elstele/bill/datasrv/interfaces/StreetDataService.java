package com.elstele.bill.datasrv.interfaces;


import com.elstele.bill.domain.Street;

import java.util.List;

public interface StreetDataService {
    public List<Street> getListOfStreets(String query);
}
