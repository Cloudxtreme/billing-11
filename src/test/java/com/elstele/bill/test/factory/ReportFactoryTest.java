package com.elstele.bill.test.factory;


import com.elstele.bill.reportCreators.factory.ReportCreatorFactory;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;
import com.elstele.bill.utils.exceptions.IncorrectReportNameException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ReportFactoryTest {

    private String reportName;
    ReportCreatorFactory reportCreatorFactory = new ReportCreatorFactory();

    public ReportFactoryTest(String reportName) {
        this.reportName = reportName;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getTestData() {
        return Arrays.asList(new Object[][]{
                {"longReport"},
                {"shortReport"},
                {"longReportRA"},
                {"localCallsMainReport"}
        });
    }

    @Test
    public void factoryCreateTest() throws IncorrectReportNameException {
        ReportCreator reportCreator = reportCreatorFactory.getCreator(reportName);
        assertNotNull(reportCreator);
    }
}
