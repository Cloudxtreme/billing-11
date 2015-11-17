package com.elstele.bill.datasrv.implementes;

import com.elstele.bill.assembler.CallForCSVAssembler;
import com.elstele.bill.dao.interfaces.CallForCSVDAO;
import com.elstele.bill.datasrv.interfaces.CallForCSVDataService;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.form.CallForCSVForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CallForCSVDataServiceImpl implements CallForCSVDataService {
    @Autowired
    CallForCSVDAO callForCSVDAO;

    @Transactional
    public void addReportData(CallForCSVForm callForCSVForm) {
        CallForCSVAssembler assembler = new CallForCSVAssembler();
        CallForCSV bean = assembler.fromFormToBean(callForCSVForm);
        callForCSVDAO.create(bean);
    }

    @Transactional
    public void clearReportTable() {
        callForCSVDAO.clearReportDataTable();

    }

    @Transactional
    public List<String> getUniqueNumberAWithProvider(Date startTime, Date finishTime, String provider) {
        List<String> result = callForCSVDAO.getUniqueNumberAWithProvider(startTime, finishTime, provider);
        return result;
    }

    @Transactional
    public List<String> getUniqueNumberA(Date startTime, Date finishTime) {
        List<String> result = callForCSVDAO.getUniqueNumberA(startTime, finishTime);
        return result;
    }

    @Transactional
    public List<CallForCSV> getCallForCSVByNumberA(String numberA, Date startTime, Date endTime) {
        List<CallForCSV> result = callForCSVDAO.getCallForCSVByNumberA(numberA, startTime, endTime);
        return result;
    }

    @Transactional
    public List<CallForCSV> getCallForCSVByNumberAWithProvider(String numberA, Date startTime, Date endTime, String provider) {
        List<CallForCSV> result = callForCSVDAO.getCallForCSVByNumberAWithProvider(numberA, startTime, endTime, provider);
        return result;
    }

    @Transactional
    public String getDescriptionFromDirections(String dirPrefix) {
        String result = callForCSVDAO.getDescriptionFromDirections(dirPrefix);
        return result;
    }
}
