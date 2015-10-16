package com.elstele.bill.datasrv;

import com.elstele.bill.assembler.CallForCSVAssembler;
import com.elstele.bill.dao.CallForCSVDAO;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.form.CallForCSVForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public List<CallForCSVForm> getUniqueNumberA(String startTime, String finishTime) {

        List<CallForCSVForm> result = new ArrayList<CallForCSVForm>();
        CallForCSVAssembler assembler = new CallForCSVAssembler();

        List<CallForCSV> beans = callForCSVDAO.getUniqueNumberA(startTime, finishTime);
        for (CallForCSV curBean : beans) {
            CallForCSVForm curForm = assembler.fromBeanToForm(curBean);
            result.add(curForm);
        }
        return result;
    }

    public String getDateInterval() {
        String result = callForCSVDAO.getDateInterval();
        return  result;
    }
}
