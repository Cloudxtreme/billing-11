package com.elstele.bill.test.dao;


import com.elstele.bill.dao.impl.IpSubnetDAOImpl;
import com.elstele.bill.domain.IpSubnet;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
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
    @Autowired
    IpSubnetDAOImpl ipSubnetDAO;
    private IpSubnet subnet;
    private IpSubnet subnet1;

    @Before
    public void setUp(){
        subnet = new IpSubnet();
        ipSubnetDAO.save(subnet);

        subnet1 = new IpSubnet();
        ipSubnetDAO.save(subnet1);
    }

    @After
    public void tearDown(){
        subnet = null;
        subnet1 = null;
    }

    @Test
    public void getSubnetsTest(){
        List<IpSubnet> actual = ipSubnetDAO.getSubnets();
        assertTrue(actual.contains(subnet));
        assertTrue(actual.contains(subnet1));
    }
}
