package com.elstele.bill.test.datasrv;

import com.elstele.bill.test.builder.bean.*;
import com.elstele.bill.test.builder.form.ServiceInternetFormBuilder;
import com.elstele.bill.test.builder.form.ServiceMarkerFormBuilder;
import com.elstele.bill.test.builder.form.ServicePhoneFormBuilder;
import com.elstele.bill.test.builder.form.ServiceTypeFormBuilder;
import com.elstele.bill.assembler.ServiceAssembler;
import com.elstele.bill.dao.impl.ServiceDAOImpl;
import com.elstele.bill.datasrv.impl.ServiceDataServiceImpl;
import com.elstele.bill.domain.*;
import com.elstele.bill.form.DeviceForm;
import com.elstele.bill.form.IpForm;
import com.elstele.bill.form.ServiceForm;
import com.elstele.bill.form.ServiceTypeForm;
import com.elstele.bill.utils.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ServiceDataServiceTest {
    @Mock
    private ServiceDAOImpl serviceDAO;
    @Mock
    private ServiceAssembler serviceAssembler;
    @InjectMocks
    private ServiceDataServiceImpl serviceDataService;

    private Date currentDate = getTimestamp();
    private List<Service> listSample;
    private Service serviceMarker;
    private ServicePhone servicePhone;
    private ServiceInternet serviceInternet;

    private ServiceForm serviceMarkerForm;
    private ServiceForm servicePhoneForm;
    private ServiceForm serviceInternetForm;

    @Before
    public void setUp(){
        beanSetUp();
        formSetUp();
    }

    @Test
    public void a_getServiceFormById(){
        when(serviceDAO.getById(1)).thenReturn(serviceMarker);
        when(serviceDAO.getById(2)).thenReturn(servicePhone);
        when(serviceDAO.getById(3)).thenReturn(serviceInternet);

        when(serviceAssembler.getServiceFormByBean(serviceMarker)).thenReturn(serviceMarkerForm);
        when(serviceAssembler.getServiceFormByBean(servicePhone)).thenReturn(servicePhoneForm);
        when(serviceAssembler.getServiceFormByBean(serviceInternet)).thenReturn(serviceInternetForm);

        ServiceForm serviceForm = serviceDataService.getServiceFormById(1);
        assertTrue(serviceForm.equals(serviceMarkerForm));

        serviceForm = serviceDataService.getServiceFormById(2);
        assertTrue(serviceForm.equals(servicePhoneForm));

        serviceForm = serviceDataService.getServiceFormById(3);
        assertTrue(serviceForm.equals(serviceInternetForm));
    }

    @Test
    public void b_getIdCurrentIpAddress(){
        IpForm ipForm = new IpForm();
        ipForm.setId(20);
        serviceInternetForm.getServiceInternet().setIp(ipForm);
        Integer ipGet = serviceDataService.getCurrentIpAddress(serviceInternetForm);
        assertTrue(ipGet.equals(20));
    }

    @Test
    public void c_getCurrentIpAddressByServiceFormId(){
        when(serviceDAO.getById(3)).thenReturn(serviceInternet);
        when(serviceAssembler.getServiceFormByBean(serviceInternet)).thenReturn(serviceInternetForm);

        IpForm ipForm = new IpForm();
        ipForm.setId(20);
        serviceInternetForm.getServiceInternet().setIp(ipForm);
        Integer ipGet = serviceDataService.getCurrentIpAddressByServiceFormId(3);
        assertTrue(ipGet.equals(20));
    }

    @Test
    public void d_addCurrentDevicePortToList(){
        when(serviceDAO.getById(3)).thenReturn(serviceInternet);
        when(serviceAssembler.getServiceFormByBean(serviceInternet)).thenReturn(serviceInternetForm);

        Integer deviceId = 2;
        Integer portId = 10;

        DeviceForm deviceForm = new DeviceForm();
        deviceForm.setId(deviceId);

        serviceInternetForm.getServiceInternet().setDevice(deviceForm);
        serviceInternetForm.getServiceInternet().setPort(portId);
        List<Integer> deviceFreePortList = new ArrayList();
        deviceFreePortList.add(1);
        deviceFreePortList.add(3);
        deviceFreePortList.add(4);
        deviceFreePortList = serviceDataService.addCurrentDevicePortToList(deviceFreePortList,3,deviceId);

        assertTrue(deviceFreePortList.contains(portId));
    }

    private void beanSetUp(){
        AccountBuilder ab = new AccountBuilder();
        Account account = ab.build().withId(3).withAccName("ACC_001").withAccType(Constants.AccountType.PRIVATE).withBalance(20f).getRes();

        ServiceTypeBuilder stb = new ServiceTypeBuilder();
        ServiceType serviceType = stb.build().withId(4).withName("name #1").withServiceType(Constants.SERVICE_PHONE).withPrice(44F).getRes();

        ServiceBuilder smb = new ServiceBuilder();
        serviceMarker = smb.build().withId(1).withDateStart(currentDate).withPeriod(Constants.Period.WEEK).withServiceType(serviceType).withAccount(account).getRes();

        ServicePhoneBuilder spb = new ServicePhoneBuilder();
        servicePhone = spb.build().withId(2).withPhoneNumber("33-44-55").withDateStart(currentDate).withPeriod(Constants.Period.MONTH).withServiceType(serviceType).withAccount(account).getRes();

        ServiceInternetBuilder sib = new ServiceInternetBuilder();
        serviceInternet = sib.build().withId(3).withUsername("User #1").withPassword("1234").withDateStart(currentDate).withPeriod(Constants.Period.MONTH).withServiceType(serviceType).withAccount(account).getRes();

        listSample = new ArrayList<>();
        listSample.add(serviceMarker);
        listSample.add(servicePhone);
        listSample.add(serviceInternet);
    }

    private void formSetUp(){
        ServiceTypeFormBuilder stb = new ServiceTypeFormBuilder();
        ServiceTypeForm serviceTypeForm = stb.build().withId(4).withName("name #1").withServiceType(Constants.SERVICE_PHONE).withPrice(44F).getRes();

        ServiceMarkerFormBuilder smfb = new ServiceMarkerFormBuilder();
        serviceMarkerForm = smfb.build().withId(1).withDateStart(currentDate).withPeriod(Constants.Period.WEEK).withServiceType(serviceTypeForm).withAccount(3).getRes();

        ServicePhoneFormBuilder spfb = new ServicePhoneFormBuilder();
        servicePhoneForm = spfb.build().withId(2).withPhoneNumber("33-44-55").withDateStart(currentDate).withPeriod(Constants.Period.MONTH).withServiceType(serviceTypeForm).withAccount(3).getRes();

        ServiceInternetFormBuilder sifb = new ServiceInternetFormBuilder();
        serviceInternetForm = sifb.build().withId(3).withUsername("User #1").withPassword("1234").withDateStart(currentDate).withPeriod(Constants.Period.MONTH).withServiceType(serviceTypeForm).withAccount(3).getRes();
    }

    private Timestamp getTimestamp(){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }

}
