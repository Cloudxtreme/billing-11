package com.elstele.bill.test.datasrv;


import com.elstele.bill.dao.interfaces.ExternalPaymentDAO;
import com.elstele.bill.datasrv.impl.ExternalPaymentDataServiceImpl;
import com.elstele.bill.domain.ExternalPaymentTransaction;
import com.elstele.bill.form.ExternalPaymentForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExternalPaymentDataServiceTest {
    @Mock
    ExternalPaymentDAO dao;
    @InjectMocks
    ExternalPaymentDataServiceImpl dataService;

    private List<ExternalPaymentTransaction> externalPaymentTransactionList;
    private List<ExternalPaymentForm> externalPaymentFormList;

    private ExternalPaymentTransaction transaction;
    private ExternalPaymentForm form;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        externalPaymentTransactionList = new ArrayList<>();
        transaction = new ExternalPaymentTransaction();
        transaction.setCheck(true);
        transaction.setId(1);
        transaction.setPayAmount(29.4f);
        transaction.setTradepoint("Test");

        ExternalPaymentTransaction transaction1 = new ExternalPaymentTransaction();
        transaction1.setCheck(false);
        transaction1.setId(2);
        transaction1.setPayAmount(339.4f);
        transaction1.setTradepoint("tessa");

        externalPaymentTransactionList.add(transaction);
        externalPaymentTransactionList.add(transaction1);

        externalPaymentFormList = new ArrayList<>();
        form = new ExternalPaymentForm();
        form.setCheck(true);
        form.setId(1);
        form.setPayAmount(29.4f);
        form.setTradepoint("Test");

        ExternalPaymentForm form1 = new ExternalPaymentForm();
        form1.setCheck(false);
        form1.setId(2);
        form1.setPayAmount(339.4f);
        form1.setTradepoint("tessa");

        externalPaymentFormList.add(form);
        externalPaymentFormList.add(form1);
    }

    @Test
    public void getExtPaymentListTest(){
        when(dao.getExtPaymentList()).thenReturn(externalPaymentTransactionList);

        List<ExternalPaymentForm> actualList = dataService.getExtPaymentList();
        assertTrue(actualList.equals(externalPaymentFormList));
    }

    @Test
    public void getLastNOfExtPaymentListTest(){
        externalPaymentTransactionList.remove(1);
        when(dao.getLastNOfExtPaymentList(1)).thenReturn(externalPaymentTransactionList);

        List<ExternalPaymentForm> actual = dataService.getLastNOfExtPaymentList(1);
        externalPaymentFormList.remove(1);
        assertEquals(actual, externalPaymentFormList);
    }

    @Test
    public void setPaymentCheckedTest(){
        when(dao.getById(1)).thenReturn(transaction);

        boolean result = dataService.setPaymentChecked(1);
        assertTrue(result);

        when(dao.getById(2)).thenReturn(null);
        boolean resultNegative = dataService.setPaymentChecked(2);
        assertFalse(resultNegative);
    }

}
