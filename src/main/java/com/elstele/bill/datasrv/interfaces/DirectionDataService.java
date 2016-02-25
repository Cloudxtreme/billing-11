package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.form.DirectionForm;
import com.elstele.bill.utils.Enums.ResponseToAjax;

import java.util.List;

public interface DirectionDataService {
    List<DirectionForm> getDirectionList(int offset, int rows);
    int getPagesCount(int pagesCount);
    String deleteDirection(int id);
    void createDirection(DirectionForm directionForm);
    DirectionForm getDirectionById(int id);
    void updateDirection(DirectionForm form);
}
