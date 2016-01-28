package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.CallAssembler;
import com.elstele.bill.dao.interfaces.CallDAO;
import com.elstele.bill.datasrv.interfaces.CallDataService;
import com.elstele.bill.domain.Call;
import com.elstele.bill.form.CallForm;
import com.elstele.bill.reportCreators.CallTO;
import com.elstele.bill.reportCreators.CallsRequestParamTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CallDataServiceImpl implements CallDataService {

    @Autowired
    CallDAO callDAO;

    @Override
    @Transactional
    public void addCalls(CallForm callForm) {
        CallAssembler assembler = new CallAssembler();
        Call call = assembler.fromFormToBean(callForm);
        callDAO.create(call);
    }

    @Override
    @Transactional
    public List<String> getUniqueNumberAFromCalls(Date startTime, Date finishTime) {
        List<String> result = callDAO.getUniqueNumberAFromCalls(startTime, finishTime);
        return result;
    }

    @Override
    @Transactional
    public List<CallTO> getCallByNumberA(String numberA, Date startTime, Date endTime) {
        List<CallTO> result = callDAO.getCallByNumberA(numberA, startTime, endTime);
        return result;
    }

    @Override
    @Transactional
    public List<String> getUniqueNumberAFromCallsWithTrunk(Date startTime, Date finishTime, String outputTrunk) {
        List<String> result = callDAO.getUniqueNumberAFromCallsWithTrunk(startTime, finishTime, outputTrunk);
        return result;
    }

    @Override
    @Transactional
    public List<CallTO> getCallByNumberAWithTrunk(String numberA, Date startTime, Date finishTime, String outputTrunk) {
        List<CallTO> result = callDAO.getCallByNumberAWithTrunk(numberA, startTime, finishTime, outputTrunk);
        return result;
    }

    @Override
    @Transactional
    public List<String> getUniqueLocalNumberAFromCalls(Date startTime, Date finishTime) {
        List<String> result = callDAO.getUniqueLocalNumberAFromCalls(startTime, finishTime);
        return result;
    }

    @Override
    @Transactional
    public List<Call> getLocalCalls(String numberA, Date startTime, Date endTime) {
        List<Call> result = callDAO.getLocalCalls(numberA, startTime, endTime);
        return result;
    }

    @Override
    @Transactional
    public List<String> getYearsList() {
        List<String> yearsList = callDAO.getYearsList();
        return yearsList;
    }

    @Transactional
    public Integer getUnbilledCallsCount() {
        return callDAO.getUnbilledCallsCount();
    }

    @Transactional
    public List<Integer> getUnbilledCallsIdList(int limit, int offset) {
        return callDAO.getUnbilledCallIds(limit, offset);
    }

    @Override
    @Transactional
    public List<CallForm> getCallsList(CallsRequestParamTO paramTO) {
        List<CallForm> result = new ArrayList<>();
        CallAssembler assembler = new CallAssembler();
        int page = paramTO.getPage() - 1;
        paramTO.setOffset(page * paramTO.getRows());
        List<Call> beans;

        if (paramTO.getCallNumberA().isEmpty() && paramTO.getCallNumberB().isEmpty() && paramTO.getSelectedTime().isEmpty()) {
            beans = callDAO.callsListSelectionBySearch(paramTO);
        } else {
            paramTO.setStartDate(paramTO.getSelectedTime());
            paramTO.setEndDate(paramTO.getSelectedTime());
            beans = callDAO.callsListSelectionBySearch(paramTO);
        }
        for (Call curBean : beans) {
            CallForm curForm = assembler.fromBeanToForm(curBean);
            result.add(curForm);
        }
        return result;
    }

    @Override
    @Transactional
    public int determineTotalPagesForOutput(CallsRequestParamTO callsRequestParamTO) {
        int callsCount;
        if (callsRequestParamTO.getStartDate() == null && callsRequestParamTO.getEndDate() == null &&
                (callsRequestParamTO.getCallNumberA() == null || callsRequestParamTO.getCallNumberA().isEmpty())
                && (callsRequestParamTO.getCallNumberB() == null || callsRequestParamTO.getCallNumberB().isEmpty())) {
            callsCount = callDAO.getCallsCount();
        } else {
            callsCount = callDAO.getCallsCountWithSearchValues(callsRequestParamTO);
            ;
        }
        int containedCount = callsRequestParamTO.getPageResults();

        return calculatePagesCount(callsCount, containedCount);
    }

    private int calculatePagesCount(int callsCount, int containedCount) {
        if (callsCount % containedCount == 0)
            return callsCount / containedCount;
        else
            return (callsCount / containedCount) + 1;
    }

}
