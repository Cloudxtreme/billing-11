package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.form.DirectionForm;

import java.util.List;

public interface DirectionDataService {
    public List<DirectionForm> getDirectionList(int offset, int rows);
    int getPagesCount(int pagesCount);
}
