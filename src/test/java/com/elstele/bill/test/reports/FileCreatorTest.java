package com.elstele.bill.test.reports;


import com.elstele.bill.reportCreators.FileCreator;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportConstants.ReportConstants;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;
import org.junit.After;
import org.junit.AfterClass;
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

    private ReportDetails reportDetails;
    private static File file;
    private static File fileDir;
    private static File fileMainFolder;

    @Before
    public void setUp(){
        reportDetails = new ReportDetails();
        reportDetails.setMonth("11");
        reportDetails.setYear("2015");
        reportDetails.setReportName("longReport");
        reportDetails.setPath(ReportConstants.REPORTS_PATH);
    }

    @AfterClass
    public static void tearDown(){
        fileMainFolder.delete();
        fileDir.delete();
        file.delete();
    }


    @Test
    public void createFileForWritingTest(){
        String pathDir = FileCreator.createFolderWithDate(reportDetails);
        file = new File(pathDir + File.separator + reportDetails.getYear() + "-" + reportDetails.getMonth() + "_" + reportDetails.getReportName() + ".txt");
        ps = FileCreator.createFileForWriting(reportDetails);
        assertNotNull(ps);
        assertTrue(file.exists());
        assertTrue(file.isFile());
    }

    @Test
    public void createMainFolderTest(){
        fileDir = new File(reportDetails.getPath());
        FileCreator.createMainFolder(reportDetails.getPath());
        assertTrue(fileDir.exists());
        assertTrue(fileDir.isDirectory());
    }

    @Test
    public void createFolderWithDateTest(){
        fileMainFolder = new File(reportDetails.getPath() + File.separator + reportDetails.getYear() + "-" + reportDetails.getMonth());
        FileCreator.createFolderWithDate(reportDetails);
        assertTrue(fileMainFolder.exists());
        assertTrue(fileMainFolder.isDirectory());

    }
}
