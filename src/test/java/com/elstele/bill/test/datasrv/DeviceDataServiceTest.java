package com.elstele.bill.test.datasrv;

import com.elstele.bill.Builders.bean.AddressBuilder;
import com.elstele.bill.Builders.bean.DeviceBuilder;
import com.elstele.bill.Builders.bean.DeviceTypeBuilder;
import com.elstele.bill.Builders.bean.IpBuilder;
import com.elstele.bill.dao.interfaces.DeviceDAO;
import com.elstele.bill.dao.interfaces.DeviceTypesDAO;
import com.elstele.bill.dao.interfaces.IpDAO;
import com.elstele.bill.dao.interfaces.StreetDAO;
import com.elstele.bill.datasrv.impl.DeviceDataServiceImpl;
import com.elstele.bill.datasrv.interfaces.IpDataService;
import com.elstele.bill.datasrv.interfaces.AuditedObjectDataService;
import com.elstele.bill.domain.*;
import com.elstele.bill.form.AddressForm;
import com.elstele.bill.form.DeviceForm;
import com.elstele.bill.form.DeviceTypesForm;
import com.elstele.bill.form.IpForm;
import com.elstele.bill.Builders.form.AddressFormBuilder;
import com.elstele.bill.Builders.form.DeviceFormBuilder;
import com.elstele.bill.Builders.form.DeviceTypeFormBuilder;
import com.elstele.bill.Builders.form.IpFormBuilder;
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
public class DeviceDataServiceTest {
    @Mock
    DeviceDAO deviceDAO;

    @Mock
    DeviceTypesDAO deviceTypesDAO;

    @Mock
    IpDAO ipDAO;

    @Mock
    StreetDAO streetDAO;

    @Mock
    IpDataService ipDataService;

    @Mock
    AuditedObjectDataService auditedObjectDataService;
    @Mock
    AuditedObject auditedObject;

    @InjectMocks
    DeviceDataServiceImpl deviceDataService;

    private List<Device> deviceList;
    private Device device;
    private DeviceForm form;
    private DeviceFormBuilder deviceFormBuilder;
    private AddressFormBuilder addressFormBuilder;

    @Before
    public void setUp() {
        deviceDataService = new DeviceDataServiceImpl();
        MockitoAnnotations.initMocks(this);

        deviceList = new ArrayList<>();
        DeviceBuilder deviceBuilder = new DeviceBuilder();
        DeviceTypeBuilder deviceTypeBuilder = new DeviceTypeBuilder();
        IpBuilder ipBuilder = new IpBuilder();
        AddressBuilder addressBuilder = new AddressBuilder();

        DeviceTypes deviceTypes= deviceTypeBuilder.build().withId(4).getRes();
        Ip ip = ipBuilder.build().withId(5).getRes();
        Address address = addressBuilder.build().withId(5).withStreet("Street").withBuilding("5").withFlat("5").getRes();
        device = deviceBuilder.build().withId(1).withName("device1").withIpAdd(ip).withDeviceType(deviceTypes).withAddress(address).getRes();

        Device device1 = deviceBuilder.build().withId(2).withName("device2").withIpAdd(ip).withDeviceType(deviceTypes).getRes();
        Device device2 = deviceBuilder.build().withId(2).withName("device3").withIpAdd(ip).withDeviceType(deviceTypes).getRes();

        deviceList.add(device);
        deviceList.add(device1);
        deviceList.add(device2);

        deviceFormBuilder = new DeviceFormBuilder();
        addressFormBuilder = new AddressFormBuilder();
        IpFormBuilder ipFormBuilder = new IpFormBuilder();
        DeviceTypeFormBuilder deviceTypeFormBuilder = new DeviceTypeFormBuilder();

        IpForm ipForm = ipFormBuilder.build().withId(5).getRes();
        DeviceTypesForm deviceTypesForm = deviceTypeFormBuilder.build().withId(4).getRes();
        AddressForm addressForm = addressFormBuilder.build().withId(5).withStreet("Street").withBuilding("5").withFlat("5").getRes();
        form = deviceFormBuilder.build().withId(1).withName("device1").withIpForm(ipForm).withDeviceTypeForm(deviceTypesForm).withAddressForm(addressForm).getRes();
    }

    @After
    public void tearDown() {
        deviceList = null;
        deviceDataService = null;
    }

    @Test
    public void getDevicesTest() {
        List<Device> oneDeviceInList = new ArrayList<>();
        oneDeviceInList.add(device);
        when(deviceDAO.getDevices()).thenReturn(oneDeviceInList);

        List<DeviceForm> actual = deviceDataService.getDevices();
        assertTrue(actual.contains(form));
    }

    /*@Test
    public void addDeviceTest() {
        when(streetDAO.getStreetIDByStreetName(form.getDeviceAddressForm().getStreet())).thenReturn(5);
        when(deviceDAO.create(device)).thenReturn(11);
        int actualId = deviceDataService.addDevice(form, session);
        assertTrue(actualId == 11);
    }*/

    @Test
    public void gettingCorrectIDForCurrentFormAndCurrentStreetTest(){
        AddressForm addressForm = addressFormBuilder.build().withStreet("Street").getRes();
        when(streetDAO.getStreetIDByStreetName(addressForm.getStreet())).thenReturn(11);
        DeviceForm form = deviceFormBuilder.build().withAddressForm(addressForm).getRes();
        deviceDataService.gettingCorrectIDForCurrentFormAndCurrentStreet(form);
        assertTrue(form.getDeviceAddressForm().getStreetId().equals(11));
    }

    @Test
    public void deleteDeviceTest(){
        int id = deviceDAO.create(device);
        deviceDataService.deleteDevice(id, "test");
        when(deviceDAO.getById(id)).thenReturn(device);
        DeviceForm form = deviceDataService.getById(id);
        assertEquals(form.getName(), "device1");
    }

    @Test
    public void getByIdTest(){
        when(deviceDAO.getById(1)).thenReturn(device);
        DeviceForm form = deviceDataService.getById(1);
        assertEquals(form.getName(), "device1");
    }
}
