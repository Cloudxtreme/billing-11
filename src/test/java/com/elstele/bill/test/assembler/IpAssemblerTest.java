package com.elstele.bill.test.assembler;

import com.elstele.bill.assembler.IpAssembler;
import com.elstele.bill.domain.Ip;
import com.elstele.bill.domain.IpSubnet;
import com.elstele.bill.form.IpForm;
import com.elstele.bill.form.IpSubnetForm;
import com.elstele.bill.utils.Enums.Status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
public class IpAssemblerTest {

    private IpAssembler assembler;
    private Ip ip ;
    private IpForm ipForm;
    private IpSubnet subnet;
    private IpSubnetForm subnetForm;

    @Before
    public void setUp(){
        assembler = new IpAssembler();

        ip = new Ip();
        ip.setId(1);
        ip.setIpName("10.0.0.1");

        ipForm = new IpForm();
        ipForm.setId(1);
        ipForm.setIpName("10.0.0.1");

        subnet = new IpSubnet();
        subnet.setId(2);
        subnet.setIpSubnet("10.0.0.2");

        subnetForm = new IpSubnetForm();
        subnetForm.setId(2);
        subnetForm.setIpSubnet("10.0.0.2");
    }

    @Test
    public void fromBeanToFormTest(){
        IpForm actual = assembler.fromBeanToForm(ip);
        assertEquals(actual.getId(), ip.getId());
        assertEquals(actual.getIpName(), ip.getIpName());
    }

    @Test
    public void fromFormToBeanTest(){
        Ip actual = assembler.fromFormToBean(ipForm);
        assertEquals(actual.getId(), ipForm.getId());
        assertEquals(actual.getIpName(), ipForm.getIpName());
    }

    @Test
     public void fromBeanToFormIpSubnetTest(){
        IpSubnetForm actual = assembler.fromBeanToFormIpSubnet(subnet);
        assertEquals(actual.getId(), subnet.getId());
        assertEquals(actual.getIpSubnet(), subnet.getIpSubnet());
    }

    @Test
    public void fromFormToBeanIpSubnetTest(){
        IpSubnet actual = assembler.fromFormToBeanIpSubnet(subnetForm);
        assertEquals(actual.getId(), subnetForm.getId());
        assertEquals(actual.getIpSubnet(), subnetForm.getIpSubnet());
    }
}
