package com.elstele.bill.dao.interfaces;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.Direction;

import java.util.List;

public interface DirectionDAO extends CommonDAO<Direction>{
    public List<Direction> getDirectionList(int offset, int rows);
    int getPagesCount();
}
