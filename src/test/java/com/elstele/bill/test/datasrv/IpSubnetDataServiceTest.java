package com.elstele.bill.test.datasrv;

import com.elstele.bill.dao.interfaces.IpSubnetDAO;
import com.elstele.bill.datasrv.impl.IpSubnetDataServiceImpl;
import com.elstele.bill.domain.Ip;
import com.elstele.bill.domain.IpSubnet;
import com.elstele.bill.form.IpSubnetForm;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class IpSubnetDataServiceTest {
    @Mock
    IpSubnetDAO ipSubnetDAO;

    @InjectMocks
    IpSubnetDataServiceImpl ipSubnetDataService;

    private IpSubnet subnet1;

    @Before
    public void setUp(){
        ipSubnetDataService = new IpSubnetDataServiceImpl();
        MockitoAnnotations.initMocks(this);

        subnet1 = new IpSubnet();
        subnet1.setId(1);
        subnet1.setIpSubnet("192.168.0.1");
    }

    @After
    public void tearDown(){
        ipSubnetDataService = null;
        subnet1 = null;
    }

    @Test
    public void getIpSubnetsTest(){
        List<IpSubnet> subnetList = new ArrayList<>();
        subnetList.add(subnet1);
        when(ipSubnetDAO.getSubnets()).thenReturn(subnetList);

        IpSubnetForm ipSubnetForm = new IpSubnetForm();
        ipSubnetForm.setId(1);
        ipSubnetForm.setIpSubnet("192.168.0.1");

        List<IpSubnetForm> actual = ipSubnetDataService.getIpSubnets();
        assertTrue(actual.contains(ipSubnetForm));
    }

}
