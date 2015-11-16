package com.elstele.bill.test.datasrv;


import com.elstele.bill.datasrv.ReportDataServiceImpl;

import com.elstele.bill.reportCreators.factory.ReportCreatorFactory;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ReportDataServiceTest {
    @Mock
    ReportCreatorFactory factory;
    ReportDetails reportDetails;
    @InjectMocks
    private ReportDataServiceImpl reportDataService;

    @Before
    public void fullReportDetails(){
        reportDetails.setMonth("01");
        reportDetails.setPath("resources\\files\\csvFiles");
        reportDetails.setReportName("longReport");
        reportDetails.setYear("2000");
    }

    @Test
    public void testCorrectGettingObjectFromFactory(){
        ReportCreator reportCreator = factory.getCreator(reportDetails.getReportName());
        assertNotNull(reportCreator);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testEmptyArrayEntrance(){
        String[] testArray = new String[0];
        reportDataService.createReport(testArray);
    }

    @After
    public void eraseDataFromReportDetails(){
        reportDetails = null;
    }



}
