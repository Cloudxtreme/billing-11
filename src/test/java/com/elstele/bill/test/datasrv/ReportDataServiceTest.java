package com.elstele.bill.test.datasrv;


import com.elstele.bill.datasrv.impl.ReportDataServiceImpl;

import com.elstele.bill.datasrv.interfaces.CallDataService;
import com.elstele.bill.reportCreators.creatorsImpl.LongGeneralReportCreatorImpl;
import com.elstele.bill.reportCreators.factory.ReportCreatorFactory;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;

import com.elstele.bill.exceptions.IncorrectReportNameException;
import com.elstele.bill.utils.Constants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

import static org.junit.Assert.*;
import static com.elstele.bill.utils.Constants.PATH_TO_CSV_FOLDER;

@RunWith(MockitoJUnitRunner.class)
public class ReportDataServiceTest {

    private ReportCreatorFactory factory;
    private ReportDetails reportDetails;

    @InjectMocks
    private ReportDataServiceImpl reportDataService;
    @Mock
    HttpSession session;
    @Autowired
    CallDataService callDataService;

    @Before
    public void fullReportDetails() {
        factory  = new ReportCreatorFactory();
        reportDetails = new ReportDetails();
        reportDetails.setMonth("01");
        reportDetails.setPath(PATH_TO_CSV_FOLDER);
        reportDetails.setReportName("longReport");
        reportDetails.setYear("2000");
    }

    @Test
    public void testCorrectGettingObjectFromFactory() throws IncorrectReportNameException {
        ReportCreator reportCreator = factory.getCreator(reportDetails.getReportName());
        assertTrue(reportCreator instanceof LongGeneralReportCreatorImpl);
    }

    @Test
    public void testEmptyArrayEntrance() throws IncorrectReportNameException {
        String[] testArray = new String[0];
        reportDataService.createReport(testArray, session);
    }

    @After
    public void eraseDataFromReportDetails() {
        reportDetails = null;
    }
}
