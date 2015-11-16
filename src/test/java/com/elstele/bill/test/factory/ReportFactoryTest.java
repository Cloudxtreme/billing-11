package com.elstele.bill.test.factory;

import com.elstele.bill.datasrv.CallDataService;
import com.elstele.bill.datasrv.CallDataServiceImpl;
import com.elstele.bill.reportCreators.creatorsImpl.LongGeneralReportCreatorImpl;
import com.elstele.bill.reportCreators.creatorsImpl.ShortGeneralReportCreatorImpl;
import com.elstele.bill.reportCreators.factory.ReportCreatorFactory;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ReportFactoryTest {

    private String reportName;
    private ReportCreator expectedCreator;
    static CallDataService callDataService = new CallDataServiceImpl();
    ReportCreatorFactory reportCreatorFactory = new ReportCreatorFactory();

    public ReportFactoryTest(String reportName, ReportCreator expectedCreator) {
        this.reportName = reportName;
        this.expectedCreator = expectedCreator;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getTestData() {
        return Arrays.asList(new Object[][]{
                {"longReport", new LongGeneralReportCreatorImpl(callDataService)},
                {"shortReport", new ShortGeneralReportCreatorImpl(callDataService)},
                {"IncorrectData", null},
                {"IncorrectData2", null}
        });
    }

    @Test
    public void factoryCreateTest() {
        ReportCreator reportCreator = reportCreatorFactory.getCreator(reportName);
        assertEquals(expectedCreator, reportCreator);
    }
}
