package com.elstele.bill.test.dao;

import com.elstele.bill.dao.impl.UserActivityDAOImpl;
import com.elstele.bill.domain.Activity;
import com.elstele.bill.utils.Enums.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
@TransactionConfiguration
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserActivityDAOTest {

    @Autowired
    UserActivityDAOImpl userActivityDAO;
    private List<Activity> expected;
    private Activity activity4;

    @Before
    public void setUp(){
        expected = new ArrayList<>();
        Activity activity1 = new Activity();
        activity1.setName("Test1");
        activity1.setStatus(Status.ACTIVE);
        expected.add(activity1);
        userActivityDAO.save(activity1);

        Activity activity2 = new Activity();
        activity2.setName("Test2");
        activity2.setStatus(Status.ACTIVE);
        expected.add(activity2);
        userActivityDAO.save(activity2);

        Activity activity3 = new Activity();
        activity3.setName("Test3");
        expected.add(activity3);
        userActivityDAO.save(activity3);

        activity4 = new Activity();
        activity4.setName("Test4");
        activity4.setStatus(Status.DELETED);
        userActivityDAO.save(activity4);
    }

    @After
    public void tearDown(){
        activity4 = null;
        expected = null;
    }

    @Test
    public void listActivityTest(){
        List<Activity> actual = userActivityDAO.listActivity();
        assertEquals(actual, expected);
        assertFalse(actual.contains(activity4));
    }
}
