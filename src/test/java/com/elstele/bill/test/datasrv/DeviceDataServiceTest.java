package com.elstele.bill.test.datasrv;

import com.elstele.bill.dao.interfaces.DeviceDAO;
import com.elstele.bill.dao.interfaces.DeviceTypesDAO;
import com.elstele.bill.dao.interfaces.IpDAO;
import com.elstele.bill.datasrv.impl.DeviceDataServiceImpl;
import com.elstele.bill.domain.*;
import com.elstele.bill.form.AddressForm;
import com.elstele.bill.form.DeviceForm;
import com.elstele.bill.form.DeviceTypesForm;
import com.elstele.bill.form.IpForm;
import com.elstele.bill.test.builder.bean.*;
import com.elstele.bill.test.builder.form.DeviceFormBuilder;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.utils.Enums.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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

    @InjectMocks
    DeviceDataServiceImpl deviceDataService;

    private List<Device> deviceList;
    private Device device;
    private DeviceForm form;
    private DeviceFormBuilder deviceFormBuilder;

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
        Address address = addressBuilder.build().withId(5).getRes();
        device = deviceBuilder.build().withId(1).withName("device1").withIpAdd(ip).withDeviceType(deviceTypes).withAddress(address).getRes();

        Device device1 = deviceBuilder.build().withId(2).withName("device2").withIpAdd(ip).withDeviceType(deviceTypes).getRes();
        Device device2 = deviceBuilder.build().withId(2).withName("device3").withIpAdd(ip).withDeviceType(deviceTypes).getRes();

        deviceList.add(device);
        deviceList.add(device1);
        deviceList.add(device2);

        DeviceTypesForm deviceTypesForm = new DeviceTypesForm();
        deviceTypesForm.setId(4);
        IpForm ipForm = new IpForm();
        ipForm.setId(5);
        AddressForm addressForm = new AddressForm();
        addressForm.setId(5);

        deviceFormBuilder = new DeviceFormBuilder();
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

    @Test
    @Ignore
    public void addDeviceTest() {
        DeviceTypesForm deviceTypesForm = new DeviceTypesForm();
        deviceTypesForm.setId(10);
        AddressForm addressForm = new AddressForm();
        addressForm.setId(5);

        DeviceForm form = deviceFormBuilder.build().withId(1).withName("device1").withDeviceTypeForm(deviceTypesForm).withAddressForm(addressForm).getRes();

        DeviceTypes deviceTypes= new DeviceTypes();
        deviceTypes.setId(10);
        when(deviceTypesDAO.getById(form.getDevType().getId())).thenReturn(deviceTypes);

        Ip ip = new Ip();
        ip.setId(5);

        IpForm ipForm = new IpForm();
        ipForm.setId(5);
        form.setIpForm(ipForm);
        when(ipDAO.getById(form.getIpForm().getId())).thenReturn(ip);

        when(deviceDAO.create(device)).thenReturn(0);
        Integer actual = deviceDataService.addDevice(form);
        assertTrue(actual.equals(0));
    }

    @Test
    public void deleteDeviceTest(){
        int id = deviceDAO.create(device);
        deviceDataService.deleteDevice(id);
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
