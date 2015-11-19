package com.elstele.bill.test.reports;


import com.elstele.bill.reportCreators.FileCreator;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportConstants.ReportConstants;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.PrintStream;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FileCreatorTest {

    @Mock
    PrintStream ps;

    ReportDetails reportDetails;

    @Before
    public void setUp(){
        reportDetails = new ReportDetails();
        reportDetails.setMonth("11");
        reportDetails.setYear("2015");
        reportDetails.setReportName("longReport");
        reportDetails.setPath(ReportConstants.REPORTS_PATH);
    }


    @Test
    public void createFileForWritingTest(){
        ps = FileCreator.createFileForWriting(reportDetails);
        assertNotNull(ps);
    }
}
