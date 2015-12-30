package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.ExternalPaymentAssembler;
import com.elstele.bill.dao.interfaces.ExternalPaymentDAO;
import com.elstele.bill.datasrv.interfaces.ExternalPaymentDataService;
import com.elstele.bill.domain.ExternalPaymentTransaction;
import com.elstele.bill.form.ExternalPaymentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan on 15/12/27.
 */
public class ExternalPaymentDataServiceImpl implements ExternalPaymentDataService {

    @Autowired
    private ExternalPaymentDAO dao;

    @Transactional
    public List<ExternalPaymentForm> getExtPaymentList() {
        List<ExternalPaymentTransaction> payments =  dao.getExtPaymentList();
        ExternalPaymentAssembler assembler = new ExternalPaymentAssembler();
        List <ExternalPaymentForm> forms =  new ArrayList<>();
        for (ExternalPaymentTransaction curBean : payments){
            ExternalPaymentForm curForm = assembler.fromBeanToForm(curBean);
            forms.add(curForm);
        }
        return forms;
    }
}
