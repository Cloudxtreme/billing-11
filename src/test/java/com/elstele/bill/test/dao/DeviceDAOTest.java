package com.elstele.bill.test.dao;


import com.elstele.bill.dao.impl.DeviceDAOImpl;
import com.elstele.bill.dao.impl.DeviceTypesDAOImpl;
import com.elstele.bill.dao.impl.IpDAOImpl;
import com.elstele.bill.dao.impl.IpSubnetDAOImpl;
import com.elstele.bill.domain.Device;
import com.elstele.bill.domain.DeviceTypes;
import com.elstele.bill.domain.Ip;
import com.elstele.bill.domain.IpSubnet;
import com.elstele.bill.test.builder.bean.DeviceBuilder;
import com.elstele.bill.test.builder.bean.DeviceTypeBuilder;
import com.elstele.bill.test.builder.bean.IpBuilder;
import com.elstele.bill.test.builder.bean.IpSubnetBuilder;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
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
    @Autowired
    SessionFactory sessionFactory;

    private Device device1;
    private Device device2;

    @Before
    public void setUp(){
        DeviceBuilder deviceBuilder = new DeviceBuilder();
        DeviceTypeBuilder deviceTypeBuilder = new DeviceTypeBuilder();
        IpBuilder ipBuilder = new IpBuilder();

        DeviceTypes types1 = deviceTypeBuilder.build().getRes();
        deviceTypesDAO.save(types1);

        IpSubnet subnet = new IpSubnet();
        ipSubnetDAO.save(subnet);

        Ip ip1 = ipBuilder.build().withIpSubnet(subnet).getRes();
        ipDAO.save(ip1);

        Ip ip2 = ipBuilder.build().withIpSubnet(subnet).getRes();
        ipDAO.save(ip2);

        device1 = deviceBuilder.build().withDeviceType(types1).withIpAdd(ip1).getRes();
        deviceDAO.save(device1);

        device2 = deviceBuilder.build().withDeviceType(types1).withIpAdd(ip2).getRes();
        deviceDAO.save(device2);
    }

    @After
    public void tearDown(){
        device1 = null;
        device2 = null;
    }

    @Test
    public void getDevicesTest(){
        List<Device> actual = deviceDAO.getDevices();
        assertTrue(actual.contains(device1));
        assertTrue(actual.contains(device2));
    }

}
