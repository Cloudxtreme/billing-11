package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.DirectionAssembler;
import com.elstele.bill.dao.interfaces.DirectionDAO;
import com.elstele.bill.datasrv.interfaces.DirectionDataService;
import com.elstele.bill.domain.Direction;
import com.elstele.bill.form.DirectionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectionDataServiceImpl implements DirectionDataService {
    @Autowired
    DirectionDAO directionDAO;

    @Override
    @Transactional
    public List<DirectionForm> getDirectionList(int page, int rows) {
        page = page - 1;
        int offset = page * rows;
        List<Direction> beansList = directionDAO.getDirectionList(offset, rows);
        DirectionAssembler assembler = new DirectionAssembler();
        List<DirectionForm> formList = new ArrayList<>();
        for(Direction direction : beansList){
            formList.add(assembler.fromBeanToForm(direction));
        }
        return formList;
    }

    @Override
    @Transactional
    public int getPagesCount(int pagesCount) {
        return calculatePagesCount(directionDAO.getPagesCount(), pagesCount);
    }

    private int calculatePagesCount(int callsCount, int containedCount) {
        if (callsCount % containedCount == 0)
            return callsCount / containedCount;
        else
            return (callsCount / containedCount) + 1;
    }
}
