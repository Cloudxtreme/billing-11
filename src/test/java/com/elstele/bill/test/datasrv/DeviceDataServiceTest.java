package com.elstele.bill.test.datasrv;

import com.elstele.bill.dao.interfaces.DeviceDAO;
import com.elstele.bill.dao.interfaces.DeviceTypesDAO;
import com.elstele.bill.dao.interfaces.IpDAO;
import com.elstele.bill.datasrv.impl.DeviceDataServiceImpl;
import com.elstele.bill.domain.Device;
import com.elstele.bill.domain.DeviceTypes;
import com.elstele.bill.domain.Ip;
import com.elstele.bill.form.DeviceForm;
import com.elstele.bill.form.DeviceTypesForm;
import com.elstele.bill.form.IpForm;
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

    @Before
    public void setUp() {
        deviceDataService = new DeviceDataServiceImpl();
        MockitoAnnotations.initMocks(this);

        deviceList = new ArrayList<>();

        device = new Device();
        device.setId(1);
        device.setStatus(Status.ACTIVE);
        device.setName("device1");

        DeviceTypes deviceTypes= new DeviceTypes();
        deviceTypes.setId(4);
        Ip ip = new Ip();
        ip.setId(5);
        device.setDeviceType(deviceTypes);
        device.setIpAdd(ip);

        Device device1 = new Device();
        device1.setId(2);
        device1.setStatus(Status.ACTIVE);
        device1.setName("device2");

        Device device2 = new Device();
        device2.setId(2);
        device2.setStatus(Status.DELETED);
        device2.setName("device3");

        deviceList.add(device);
        deviceList.add(device1);
        deviceList.add(device2);

        form = new DeviceForm();
        form.setId(1);
        form.setStatus(Status.ACTIVE);
        form.setName("device1");
        DeviceTypesForm deviceTypesForm = new DeviceTypesForm();
        deviceTypesForm.setId(4);
        IpForm ipForm = new IpForm();
        ipForm.setId(5);
        form.setDevType(deviceTypesForm);
        form.setIpForm(ipForm);

    }

    @After
    public void tearDown() {
        deviceList = null;
        deviceDataService = null;
    }

    @Test
    @Ignore
    public void getDevicesTest() {
        List<Device> oneDeviceInList = new ArrayList<>();
        oneDeviceInList.add(device);
        when(deviceDAO.getDevices()).thenReturn(oneDeviceInList);

        List<DeviceForm> actual = deviceDataService.getDevices();
        assertTrue(actual.contains(form));
    }

    @Test
    public void addDeviceTest() {
        DeviceForm form = new DeviceForm();
        form.setId(1);
        form.setStatus(Status.ACTIVE);
        form.setName("device1");
        DeviceTypesForm deviceTypesForm = new DeviceTypesForm();
        deviceTypesForm.setId(10);
        form.setDevType(deviceTypesForm);

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
