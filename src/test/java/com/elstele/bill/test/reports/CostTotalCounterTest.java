package com.elstele.bill.test.reports;


import com.elstele.bill.domain.Call;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.reportCreators.CostTotalCounter;
import com.elstele.bill.reportCreators.CallTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CostTotalCounterTest {

    private List<CallTO> callTOList;
    private List<CallForCSV> callForCSVList;
    private List<Call> callList;

    @Before
    public void setUp() {
        callTOList = new ArrayList<>();
        CallTO callTO = new CallTO();
        callTO.setCosttotal(10f);
        CallTO callTO1 = new CallTO();
        callTO1.setCosttotal(20f);
        callTOList.add(callTO);
        callTOList.add(callTO1);

        callForCSVList = new ArrayList<>();
        CallForCSV callForCSV = new CallForCSV();
        callForCSV.setCostCallTotal("10");
        CallForCSV callForCSV1 = new CallForCSV();
        callForCSV1.setCostCallTotal("20");
        callForCSVList.add(callForCSV);
        callForCSVList.add(callForCSV1);

        Call call = new Call();
        call.setDuration(20000);
        Call call1 = new Call();
        call1.setDuration(40000);
        callList = new ArrayList<>();
        callList.add(call);
        callList.add(call1);
    }

    @Test
    public void countForTOTest() {
        Float costTotal = CostTotalCounter.countForTO(callTOList);
        assertTrue(costTotal == 30);
    }

    @Test
    public void countForCSVTest() {
        Float costTotalCSV = CostTotalCounter.countForCSV(callForCSVList);
        assertTrue(costTotalCSV == 30);
    }

    @Test
    public void countForCallTest(){
        Float costTotalCall = CostTotalCounter.countDurationForCall(callList);
        assertTrue(costTotalCall == 60000);
    }

    @Test
    public void countLocalForCallTest(){
        Float costTotalLocal = CostTotalCounter.countLocalForCall(callList);
        assertEquals(costTotalLocal, new Float(21.6));
    }
}
