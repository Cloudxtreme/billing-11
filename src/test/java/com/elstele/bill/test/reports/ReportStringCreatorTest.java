package com.elstele.bill.test.reports;


import com.elstele.bill.reportCreators.reportsStringCreator.ReportStringCreator;
import com.elstele.bill.utils.CallTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ReportStringCreatorTest {

    private String numberA;
    private List<CallTO> callTOList;

    @Before
    public void setUp(){
        numberA = "1234567";
        CallTO callTO = new CallTO();
        callTO.setNumberb("7654321");
        callTO.setDuration(BigInteger.valueOf(15));
        callTO.setPrefix("12");
        callTO.setDescription("99");
        callTO.setCosttotal(100.0f);
        callTO.setStarttime(new Date());

        CallTO callTO1 = new CallTO();
        callTO1.setNumberb("9876543");
        callTO1.setDuration(BigInteger.valueOf(10));
        callTO1.setPrefix("01");
        callTO1.setDescription("33");
        callTO1.setCosttotal(200.0f);
        callTO1.setStarttime(new Date());

        callTOList = new ArrayList<>();
        callTOList.add(callTO);
        callTOList.add(callTO1);
    }

    @Test
    public void roundTest(){
        double doubleTest = 11.9876;
        double expectedDouble = ReportStringCreator.round(doubleTest, 1);
        assertTrue(expectedDouble ==  12.0);
    }

    @Test
    public void stringCreateTest(){
        ReportStringCreator reportStringCreator = new ReportStringCreator();
        List<String>  expectedList = reportStringCreator.createCallTOStrings(numberA, callTOList);
        String numberAShort = numberA.substring(1, numberA.length());
        String expectedFirstString = "Номер телефона, с которого звонили: " + numberAShort;
        assertTrue(expectedList.contains(expectedFirstString));
        String expectedLastString = "--------------------------------------------------------------------------------";
        assertTrue(expectedList.contains(expectedLastString));
    }
}
