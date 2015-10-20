package com.elstele.bill.datasrv;

import com.elstele.bill.assembler.CallForCSVAssembler;
import com.elstele.bill.dao.CallForCSVDAO;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.form.CallForCSVForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CallForCSVDataServiceImpl implements CallForCSVDataService {
    @Autowired
    CallForCSVDAO callForCSVDAO;

    @Override
    @Transactional
    public void addReportData(CallForCSVForm callForCSVForm) {
        CallForCSVAssembler assembler = new CallForCSVAssembler();
        CallForCSV bean = assembler.fromFormToBean(callForCSVForm);
        callForCSVDAO.create(bean);
    }

    @Override
    @Transactional
    public void clearReportTable() {
        callForCSVDAO.clearReportDataTable();

    }

    @Override
    @Transactional
    public List<CallForCSVForm> getUniqueNumberA(Date startTime, Date finishTime) {

        List<CallForCSVForm> result = new ArrayList<CallForCSVForm>();
        CallForCSVAssembler assembler = new CallForCSVAssembler();

        List<CallForCSV> beans = callForCSVDAO.getUniqueNumberA(startTime, finishTime);
        for (CallForCSV curBean : beans) {
            CallForCSVForm curForm = assembler.fromBeanToForm(curBean);
            result.add(curForm);
        }
        return result;
    }

    @Override
    @Transactional
    public Date getDateInterval() {
        Date result = callForCSVDAO.getDateInterval();
        return  result;
    }

    @Override
    @Transactional
    public List<CallForCSVForm> getCallForCSVByNumberA(String numberA, Date startTime, Date endTime) {
        List<CallForCSVForm> result = new ArrayList<CallForCSVForm>();
        CallForCSVAssembler assembler = new CallForCSVAssembler();

        List<CallForCSV> beans = callForCSVDAO.getCallForCSVByNumberA(numberA, startTime, endTime);
        for (CallForCSV curBean : beans) {
            CallForCSVForm curForm = assembler.fromBeanToForm(curBean);
            result.add(curForm);
        }
        return result;
    }
}
