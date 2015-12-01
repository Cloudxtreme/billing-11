package com.elstele.bill.test.dao;


import com.elstele.bill.dao.impl.DeviceDAOImpl;
import com.elstele.bill.dao.impl.DeviceTypesDAOImpl;
import com.elstele.bill.dao.impl.IpDAOImpl;
import com.elstele.bill.dao.impl.IpSubnetDAOImpl;
import com.elstele.bill.domain.Device;
import com.elstele.bill.domain.DeviceTypes;
import com.elstele.bill.domain.Ip;
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
public class DeviceDAOTest {
    @Autowired
    DeviceDAOImpl deviceDAO;
    @Autowired
    DeviceTypesDAOImpl deviceTypesDAO;
    @Autowired
    IpSubnetDAOImpl ipSubnetDAO;
    @Autowired
    IpDAOImpl ipDAO;
    private List<Device> expected;

    @Before
    public void setUp(){
        expected = new ArrayList<>();

        Device device1 = new Device();
        Device device2 = new Device();

        DeviceTypes types1 = new DeviceTypes();
        deviceTypesDAO.save(types1);

        IpSubnet subnet = new IpSubnet();
        ipSubnetDAO.save(subnet);

        Ip ip1 = new Ip();
        ip1.setIpSubnet(subnet);
        ipDAO.save(ip1);

        Ip ip2 = new Ip();
        ip2.setIpSubnet(subnet);
        ipDAO.save(ip2);

        device1.setIpAdd(ip1);
        device1.setDeviceTypes(types1);
        expected.add(device1);
        deviceDAO.save(device1);

        device2.setIpAdd(ip2);
        device2.setDeviceTypes(types1);
        expected.add(device2);
        deviceDAO.save(device2);
    }

    @After
    public void tearDown(){
        expected = null;
    }

    @Test
    public void getDevicesTest(){
        List<Device> actual = deviceDAO.getDevices();
        assertEquals(actual, expected);
    }

}
