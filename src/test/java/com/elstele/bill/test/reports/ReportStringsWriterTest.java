package com.elstele.bill.test.reports;


import com.elstele.bill.reportCreators.reportConstants.ReportConstants;
import com.elstele.bill.reportCreators.reportStringsWriter.ReportStringsWriter;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static com.elstele.bill.reportCreators.reportsStringCreator.ReportStringCreator.lineSeparator;

@RunWith(MockitoJUnitRunner.class)
public class ReportStringsWriterTest {

    private File fileExpected;
    private File fileActual;
    private List<String> stringList;

    @Mock
    PrintStream ps;

    @Before
    public void setUp() throws FileNotFoundException, UnsupportedEncodingException {
        fileExpected = new File(ReportConstants.REPORTS_PATH + File.separator + "fileExpected.txt");
        fileActual = new File(ReportConstants.REPORTS_PATH + File.separator + "fileActual.txt");
        PrintWriter writer = new PrintWriter(fileExpected, "UTF-8");
        writer.println("The first line");
        writer.println("The second line");
        writer.close();

        stringList= new ArrayList<>();
        stringList.add("The first line" + lineSeparator);
        stringList.add("The second line" + lineSeparator);

        ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(fileActual, false)), true, "cp1251");
    }

    @After
    public void tearDown(){
        //fileActual.delete();
        //fileExpected.delete();
    }

    @Test
    public void writeTest() throws IOException {
        ReportStringsWriter.write(stringList, ps);
        assertTrue(FileUtils.contentEquals(fileActual, fileExpected));
    }
}
