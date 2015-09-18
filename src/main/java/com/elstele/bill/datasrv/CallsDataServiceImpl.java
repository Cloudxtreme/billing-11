package com.elstele.bill.datasrv;

import com.elstele.bill.assembler.CallsAssembler;
import com.elstele.bill.dao.CallsDAO;
import com.elstele.bill.domain.Calls;
import com.elstele.bill.form.CallsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CallsDataServiceImpl implements CallsDataService {

    @Autowired
    CallsDAO callsDAO;


    @Override
    @Transactional
    public void addCalls(CallsForm callsForm) {
        CallsAssembler assembler = new CallsAssembler();
        Calls calls = assembler.fromFormToBean(callsForm);
        callsDAO.create(calls);
    }
}
