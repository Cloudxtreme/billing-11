package com.elstele.bill.datasrv;

import com.elstele.bill.assembler.CallAssembler;
import com.elstele.bill.dao.CallDAO;
import com.elstele.bill.domain.Call;
import com.elstele.bill.form.CallForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public int getCallsCount() {
        return callDAO.getCallsCount();
    }

    @Override
    @Transactional
    public List<CallForm> getCallsList(int rows, int page) {
        List<CallForm> result = new ArrayList<CallForm>();
        CallAssembler assembler = new CallAssembler();
        page = page -1; //this is correction for User Interfase (for user page starts from 1, but we use 0 as first number)
        int offset = page*rows;
        List<Call> beans = callDAO.getCallsList(rows, offset);
        for (Call curBean : beans){
            CallForm curForm = assembler.fromBeanToForm(curBean);
            result.add(curForm);
        }
        return result;
    }
}
