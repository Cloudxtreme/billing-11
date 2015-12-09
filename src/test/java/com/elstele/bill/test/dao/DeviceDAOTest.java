package com.elstele.bill.test.dao;


import com.elstele.bill.dao.impl.DeviceDAOImpl;
import com.elstele.bill.dao.impl.DeviceTypesDAOImpl;
import com.elstele.bill.dao.impl.IpDAOImpl;
import com.elstele.bill.dao.impl.IpSubnetDAOImpl;
import com.elstele.bill.domain.Device;
import com.elstele.bill.domain.DeviceTypes;
import com.elstele.bill.domain.Ip;
import com.elstele.bill.domain.IpSubnet;
import org.junit.*;
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
public class DeviceDAOTest {
    @Autowired
    DeviceDAOImpl deviceDAO;
    @Autowired
    DeviceTypesDAOImpl deviceTypesDAO;
    @Autowired
    IpSubnetDAOImpl ipSubnetDAO;
    @Autowired
    IpDAOImpl ipDAO;

    private Device device1;
    private Device device2;

    @Before
    public void setUp(){

        //TODO use builder pattern to create objects
        device1 = new Device();
        device2 = new Device();

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
        device1.setDeviceType(types1);
        deviceDAO.save(device1);

        device2.setIpAdd(ip2);
        device2.setDeviceType(types1);
        deviceDAO.save(device2);
    }

    @After
    public void tearDown(){
        device1 = null;
        device2 = null;
    }

    @Test
    @Ignore
    //TODO: table in DB has fiels "street" and "building"  with not null constraints, but object Device have not this fields
    // need to fix this during adding Address field in Device
    // after this unignore this test
    public void getDevicesTest(){
        List<Device> actual = deviceDAO.getDevices();
        assertTrue(actual.contains(device1));
        assertTrue(actual.contains(device2));
    }

}
