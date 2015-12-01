package com.elstele.bill.test.dao;


import com.elstele.bill.dao.impl.DeviceTypesDAOImpl;
import com.elstele.bill.domain.DeviceTypes;
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

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
@TransactionConfiguration
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeviceTypesDAOTest {

    @Autowired
    DeviceTypesDAOImpl deviceTypesDAO;

    private DeviceTypes type1;
    private DeviceTypes type;

    @Before
    public void setUp() {
        type = new DeviceTypes();
        deviceTypesDAO.save(type);

        type1 = new DeviceTypes();
        deviceTypesDAO.save(type1);
    }

    @After
    public void tearDown() {
        type = null;
        type1 = null;
    }

    @Test
    public void getDeviceTypesTest() {
        List<DeviceTypes> actual = deviceTypesDAO.getDeviceTypes();
        assertTrue(actual.contains(type1));
        assertTrue(actual.contains(type));
    }
}
