package com.elstele.bill.test.datasrv;


import com.elstele.bill.datasrv.impl.ReportDataServiceImpl;

import com.elstele.bill.datasrv.interfaces.CallDataService;
import com.elstele.bill.reportCreators.creatorsImpl.LongGeneralReportCreatorImpl;
import com.elstele.bill.reportCreators.factory.ReportCreatorFactory;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;

import com.elstele.bill.exceptions.IncorrectReportNameException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ReportDataServiceTest {

    private ReportCreatorFactory factory;
    private ReportDetails reportDetails;

    @InjectMocks
    private ReportDataServiceImpl reportDataService;
    @Autowired
    CallDataService callDataService;

    @Before
    public void fullReportDetails() {
        factory  = new ReportCreatorFactory();
        reportDetails = new ReportDetails();
        reportDetails.setMonth("01");
        reportDetails.setPath("resources\\files\\csvFiles");
        reportDetails.setReportName("longReport");
        reportDetails.setYear("2000");
    }

    @Test
    public void testCorrectGettingObjectFromFactory() throws IncorrectReportNameException {
        ReportCreator reportCreator = factory.getCreator(reportDetails.getReportName());
        assertTrue(reportCreator instanceof LongGeneralReportCreatorImpl);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testEmptyArrayEntrance() throws IncorrectReportNameException {
        String[] testArray = new String[0];
        reportDataService.createReport(testArray);
    }

    @After
    public void eraseDataFromReportDetails() {
        reportDetails = null;
    }


}
