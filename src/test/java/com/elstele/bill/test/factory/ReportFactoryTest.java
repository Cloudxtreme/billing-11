package com.elstele.bill.test.factory;


import com.elstele.bill.datasrv.interfaces.CallDataService;
import com.elstele.bill.datasrv.interfaces.CallForCSVDataService;
import com.elstele.bill.reportCreators.creatorsImpl.*;
import com.elstele.bill.reportCreators.factory.ReportCreatorFactory;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;
import com.elstele.bill.utils.exceptions.IncorrectReportNameException;
import org.apache.commons.lang.ObjectUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ReportFactoryTest {

    @Autowired
    static CallDataService callDataService;
    @Autowired
    static CallForCSVDataService callForCSVDataService;
    private String reportName;
    private ReportCreator expectedCreator;
    ReportCreatorFactory reportCreatorFactory = new ReportCreatorFactory();

    public ReportFactoryTest(String reportName, ReportCreator reportCreator) {
        this.reportName = reportName;
        this.expectedCreator = reportCreator;

    }

    @Parameterized.Parameters
    public static Collection<Object[]> getTestData() {
        return Arrays.asList(new Object[][]{
                {"longReport", new LongGeneralReportCreatorImpl(callDataService)},
                {"shortReport", new ShortGeneralReportCreatorImpl(callDataService)},
                {"longReportRA",new  LongGeneralReportRACreatorImpl(callForCSVDataService)},
                {"localCallsMainReport", new LocalCallsMainGeneralReportCreatorImpl(callDataService)}
        });
    }

    @Test
    public void factoryCreateTest() throws IncorrectReportNameException {
        ReportCreator reportCreator = reportCreatorFactory.getCreator(reportName);
        assertEquals(reportCreator.getClass().getName(), expectedCreator.getClass().getName());
    }
}
