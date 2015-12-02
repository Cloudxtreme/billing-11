package com.elstele.bill.test.datasrv;

import com.elstele.bill.dao.impl.CallForCSVDAOImpl;
import com.elstele.bill.dao.interfaces.CallForCSVDAO;
import com.elstele.bill.datasrv.impl.CallForCSVDataServiceImpl;
import com.elstele.bill.datasrv.interfaces.CallForCSVDataService;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.reportCreators.reportConstants.ReportConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CallForCSVDataServiceTest {
    @Mock
    private CallForCSVDAO callForCSVDAO;

    @InjectMocks
    private CallForCSVDataServiceImpl callForCSVDataService;

    private List<String> expectedNumbersWithProvider;
    private Date startDate;
    private Date endDate;
    private List<String> expectedNumbersList;
    private CallForCSV callForCSV;
    private CallForCSV callForCSV1;
    private CallForCSV callForCSV2;

    @Before
    public void setUp() {
        callForCSVDataService = new CallForCSVDataServiceImpl();
        MockitoAnnotations.initMocks(this);

        startDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.DATE, 1);
        endDate = c.getTime();

        expectedNumbersWithProvider = new ArrayList<>();
        expectedNumbersList = new ArrayList<>();

        callForCSV = new CallForCSV();
        callForCSV.setNumberA("7891122");
        callForCSV.setStartTime(startDate);
        callForCSV.setProvider("1");

        callForCSV1 = new CallForCSV();
        callForCSV1.setNumberA("7876655");
        callForCSV1.setStartTime(startDate);
        callForCSV1.setProvider("2");
        callForCSV1.setDirPrefix("001193");

        callForCSV2 = new CallForCSV();
        callForCSV2.setNumberA("7980022");
        callForCSV2.setStartTime(startDate);
        callForCSV2.setProvider("1");

        expectedNumbersWithProvider.add(callForCSV.getNumberA());
        expectedNumbersWithProvider.add(callForCSV2.getNumberA());
    }

    @After
    public void tearDown() {
        callForCSVDataService = null;
        expectedNumbersList = null;
    }

    @Test
    public void getUniqueNumberAWithProviderTest() {
        when(callForCSVDAO.getUniqueNumberAWithProvider(startDate, endDate, ReportConstants.UKRTEL_PROVIDER)).thenReturn(expectedNumbersWithProvider);
        List<String> actualList = callForCSVDataService.getUniqueNumberAWithProvider(startDate, endDate, ReportConstants.UKRTEL_PROVIDER);
        assertEquals(expectedNumbersWithProvider, actualList);
        assertFalse(actualList.contains("7876655"));
    }

    @Test
    public void getUniqueNumberATest() {
        expectedNumbersList.add(callForCSV.getNumberA());
        expectedNumbersList.add(callForCSV1.getNumberA());
        expectedNumbersList.add(callForCSV2.getNumberA());
        when(callForCSVDAO.getUniqueNumberA(startDate, endDate)).thenReturn(expectedNumbersList);
        List<String> actualList = callForCSVDataService.getUniqueNumberA(startDate, endDate);
        assertEquals(actualList, expectedNumbersList);
        assertTrue(actualList.contains(callForCSV.getNumberA()));
    }

    @Test
    public void getCallForCSVByNumberATest() {
        List<CallForCSV> expectedList = new ArrayList<>();
        expectedList.add(callForCSV);
        when(callForCSVDAO.getCallForCSVByNumberA(callForCSV.getNumberA(), startDate, endDate)).thenReturn(expectedList);
        List<CallForCSV> actualList = callForCSVDataService.getCallForCSVByNumberA(callForCSV.getNumberA(), startDate, endDate);
        assertTrue(actualList.contains(callForCSV));
        assertEquals(actualList, expectedList);
    }

    @Test
    public void getCallForCSVByNumberAWithProviderTest() {
        List<CallForCSV> expectedList = new ArrayList<>();
        expectedList.add(callForCSV1);
        when(callForCSVDAO.getCallForCSVByNumberAWithProvider(callForCSV1.getNumberA(), startDate, endDate, ReportConstants.VEGA_PROVIDER)).thenReturn(expectedList);
        List<CallForCSV> actualList = callForCSVDataService.getCallForCSVByNumberAWithProvider(callForCSV1.getNumberA(), startDate, endDate, ReportConstants.VEGA_PROVIDER);
        assertEquals(actualList, expectedList);
        assertTrue(actualList.contains(callForCSV1));
    }

    @Test
    public void getDescriptionFromDirectionsTest() {
        String expectedResult = "Гренландия";
        when(callForCSVDAO.getDescriptionFromDirections(callForCSV1.getDirPrefix())).thenReturn(expectedResult);
        String actualResult = callForCSVDataService.getDescriptionFromDirections(callForCSV1.getDirPrefix());
        assertEquals(expectedResult, actualResult);
    }
}
