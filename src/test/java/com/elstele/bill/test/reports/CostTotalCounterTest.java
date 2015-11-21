package com.elstele.bill.test.reports;


import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.reportCreators.CostTotalCounter;
import com.elstele.bill.utils.CallTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CostTotalCounterTest {

    private List<CallTO> callTOList;
    private List<CallForCSV> callForCSVList;

    @Mock
    CostTotalCounter costTotalCounter;

    @Before
    public void setUp() {
        costTotalCounter = new CostTotalCounter();

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

    }

    @Test
    public void countForTOTest() {
        Float costTotal = costTotalCounter.countForTO(callTOList);
        assertTrue(costTotal == 30);
    }

    @Test
    public void countForCSVTest() {
        Float costTotalCSV = costTotalCounter.countForCSV(callForCSVList);
        assertTrue(costTotalCSV == 30);
    }
}
