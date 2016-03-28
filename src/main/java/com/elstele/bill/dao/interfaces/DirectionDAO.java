package com.elstele.bill.dao.interfaces;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.Direction;

import java.util.Date;
import java.util.List;

public interface DirectionDAO extends CommonDAO<Direction>{
    public List<Direction> getDirectionList(int offset, int rows, String prefix);
    public int getPagesCount(String prefix);
    public Direction getByPrefixMainPart(String prefixPart);
    public Direction getDirectionByPrefixAndDate(String prefix, Date validateFrom);
    public Integer setValidToDateForDirections(Date newDateFromFile, Date validTo);
    public Direction getDirectionForDateCorrecting(String prefix, Date validateFromNew, String getType);
    public List<Direction> getDirectionListByValidFromDate(Date validFrom);
}
