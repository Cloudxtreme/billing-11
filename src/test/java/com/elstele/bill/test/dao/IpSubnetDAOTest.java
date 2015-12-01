package com.elstele.bill.test.dao;


import com.elstele.bill.dao.impl.IpSubnetDAOImpl;
import com.elstele.bill.domain.IpSubnet;
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
public class IpSubnetDAOTest {

    List<IpSubnet> expected;
    @Autowired
    IpSubnetDAOImpl ipSubnetDAO;

    @Before
    public void setUp(){
        expected = new ArrayList<>();

        IpSubnet subnet = new IpSubnet();
        ipSubnetDAO.save(subnet);
        expected.add(subnet);

        IpSubnet subnet1 = new IpSubnet();
        ipSubnetDAO.save(subnet1);
        expected.add(subnet1);
    }

    @After
    public void tearDown(){
        expected = null;
    }

    @Test
    public void getSubnetsTest(){
        List<IpSubnet> actual = ipSubnetDAO.getSubnets();
        assertEquals(actual, expected);
    }
}
