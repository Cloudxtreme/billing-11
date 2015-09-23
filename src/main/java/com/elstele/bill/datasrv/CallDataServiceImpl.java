package com.elstele.bill.datasrv;

import com.elstele.bill.assembler.CallAssembler;
import com.elstele.bill.dao.CallDAO;
import com.elstele.bill.domain.Call;
import com.elstele.bill.form.CallForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
