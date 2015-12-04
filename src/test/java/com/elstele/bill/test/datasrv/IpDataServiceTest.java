package com.elstele.bill.test.datasrv;

import com.elstele.bill.dao.interfaces.IpDAO;
import com.elstele.bill.datasrv.impl.IpDataServiceImpl;
import com.elstele.bill.domain.Ip;
import com.elstele.bill.domain.IpSubnet;
import com.elstele.bill.form.IpForm;
import com.elstele.bill.form.IpSubnetForm;
import com.elstele.bill.utils.Enums.IpStatus;
import com.elstele.bill.utils.Enums.Status;
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
public class IpDataServiceTest {
    @Mock
    IpDAO ipDAO;

    @InjectMocks
    IpDataServiceImpl ipDataService;

    private Ip ip;
    private Ip ip1;
    private IpSubnet ipSubnet;

    @Before
    public void setUp(){
        ipDataService = new IpDataServiceImpl();
        MockitoAnnotations.initMocks(this);

        ip = new Ip();
        ip.setId(1);
        ip.setIpName("98.99.11.11");
        ipSubnet = new IpSubnet();
        ipSubnet.setIpSubnet("192.168.1.1");
        ip.setIpSubnet(ipSubnet);

        ip1 = new Ip();
        ip1.setId(2);
        ip1.setIpName("192.22.11.22");
    }

    @After
    public void tearDown(){
        ipDataService = null;
        ip = null;
        ip1 = null;
    }

    @Test
    public void getIpAddressListTest(){
        List<Ip> ipList = new ArrayList<>();
        ipList.add(ip);
        when(ipDAO.getIpAddressList()).thenReturn(ipList);

        IpForm form = new IpForm();
        form.setId(1);
        form.setIpName("98.99.11.11");
        form.setIpSubnet(ipSubnet);

        List<IpForm> actualList = ipDataService.getIpAddressList();
        assertTrue(actualList.contains(form));
    }

    @Test
    public void getBySubnetIdTest(){
        IpForm form = new IpForm();
        form.setId(1);
        form.setIpName("98.99.11.11");
        form.setIpSubnet(ipSubnet);

        List<Ip> ipList = new ArrayList<>();
        ipList.add(ip);
        when(ipDAO.getIpAddressListBySubnetId(1)).thenReturn(ipList);
        List<IpForm> actual = ipDataService.getBySubnetId(1);
        assertTrue(actual.contains(form));
    }
}
