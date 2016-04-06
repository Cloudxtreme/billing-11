package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.domain.Direction;
import com.elstele.bill.form.DirectionForm;
import com.elstele.bill.utils.Enums.ResponseToAjax;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface DirectionDataService {
    public List<DirectionForm> getDirectionList(int offset, int rows, String prefix);
    public int getPagesCount(int pagesCount, String prefix);
    public String deleteDirection(int id);
    public void createDirection(DirectionForm directionForm);
    public Integer createDirection(Direction direction);
    public DirectionForm getDirectionById(int id);
    public void updateDirection(DirectionForm form);
    public ResponseToAjax checkForFree(int id, String prefix, Long validFromDateValue);
    public Direction getByPrefixMainPart(String prefixMainPart);
    public Direction getDirectionByPrefixAndDate(String prefix, Date validateFrom);
    public Integer setValidToDateForDirections(Date newDateFromFile);
    public HashMap<String, Direction> getDirectionMapByValidFromDate(Date validFrom);
    public Direction getBiggerDate(Date validFrom);
}
