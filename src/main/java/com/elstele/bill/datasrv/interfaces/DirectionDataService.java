package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.form.DirectionForm;
import com.elstele.bill.utils.Enums.ResponseToAjax;

import java.util.List;

public interface DirectionDataService {
    public List<DirectionForm> getDirectionList(int offset, int rows, String prefix);
    public int getPagesCount(int pagesCount, String prefix);
    public String deleteDirection(int id);
    public void createDirection(DirectionForm directionForm);
    public DirectionForm getDirectionById(int id);
    public void updateDirection(DirectionForm form);
    public ResponseToAjax checkForFree(int id, String prefix);
}
