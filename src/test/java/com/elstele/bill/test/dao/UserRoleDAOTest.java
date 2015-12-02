package com.elstele.bill.test.dao;


import com.elstele.bill.dao.impl.UserRoleDAOImpl;
import com.elstele.bill.domain.UserRole;
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
public class UserRoleDAOTest {

    @Autowired
    UserRoleDAOImpl userRoleDAO;
    private List<UserRole> expectedUserRoleList;
    private UserRole role4;

    @Before
    public void setUp(){
        expectedUserRoleList = new ArrayList<>();
        UserRole role1 = new UserRole();
        role1.setName("userRole1");
        role1.setStatus(Status.ACTIVE);
        UserRole role2 = new UserRole();
        role2.setName("userRole2");
        role2.setStatus(Status.ACTIVE);
        UserRole role3 = new UserRole();
        role3.setName("userRole3");
        role4 = new UserRole();
        role4.setName("userRole4");
        role4.setStatus(Status.DELETED);
        userRoleDAO.save(role1);
        expectedUserRoleList.add(role1);
        userRoleDAO.save(role2);
        expectedUserRoleList.add(role2);
        userRoleDAO.save(role3);
        expectedUserRoleList.add(role3);
        userRoleDAO.save(role4);
    }

    @After
    public void tearDown(){
        //TODO investigate this comment
        //userRoleDAO.clearTable("userrole");
        expectedUserRoleList = null;
    }

    @Test
    public void listUserRoleTest(){
        List<UserRole> actualUserRoleList = userRoleDAO.listUserRole();
        assertEquals(actualUserRoleList, expectedUserRoleList);
        assertFalse(actualUserRoleList.contains(role4));
    }
}
