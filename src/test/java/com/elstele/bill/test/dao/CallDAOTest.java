package com.elstele.bill.test.dao;


import com.elstele.bill.dao.impl.CallDAOImpl;
import com.elstele.bill.dao.interfaces.CallDAO;
import com.elstele.bill.domain.Call;
import com.elstele.bill.utils.Status;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resources;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
@TransactionConfiguration
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WebAppConfiguration
public class CallDAOTest {

    private Date startDate;
    private Date endDate;
    private List<String> expectedList = new ArrayList<>();

    private CallDAOImpl callDAO = new CallDAOImpl();

    @Before
    public void setUp(){
        startDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.DATE, 1);
        endDate = c.getTime();

        Call call = new Call();
        call.setId(1);
        call.setStatus(Status.ACTIVE);
        call.setAonKat("1");
        call.setCallDirectionId(11);
        call.setCostPref(22.11f);
        call.setCostRegular(99.33f);
        call.setCostTotal(113.111f);
        call.setDuration(122l);
        call.setDvoCodeA("01");
        call.setDvoCodeB("02");
        call.setIkNum("123456789");
        call.setInputTrunk("01");
        call.setNumberA("1111111");
        call.setNumberB("8888888");
        call.setOutputTrunk("05");
        call.setStartTime(startDate);

        expectedList.add(call.getNumberA());

        callDAO.save(call);

        call = new Call();
        call.setId(2);
        call.setStatus(Status.ACTIVE);
        call.setAonKat("2");
        call.setCallDirectionId(9);
        call.setCostPref(22.11f);
        call.setCostRegular(99.33f);
        call.setCostTotal(113.111f);
        call.setDuration(122l);
        call.setDvoCodeA("02");
        call.setDvoCodeB("03");
        call.setIkNum("987654321");
        call.setInputTrunk("02");
        call.setNumberA("2222222");
        call.setNumberB("9999999");
        call.setOutputTrunk("014");
        call.setStartTime(startDate);

        expectedList.add(call.getNumberA());

        callDAO.save(call);

    }

    @Test
    public void testGetDistinctNumbers(){
        List<String> numbersList = callDAO.getUniqueNumberAFromCalls(startDate, endDate);
        assertEquals(numbersList, expectedList);
    }

}
