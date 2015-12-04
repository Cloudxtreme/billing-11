package com.elstele.bill.test.datasrv;

import com.elstele.bill.dao.interfaces.DeviceTypesDAO;
import com.elstele.bill.datasrv.impl.DeviceTypesDataServiceImpl;
import com.elstele.bill.datasrv.interfaces.DeviceTypesDataService;
import com.elstele.bill.domain.DeviceTypes;
import com.elstele.bill.form.DeviceTypesForm;
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
public class DeviceTypesDataServiceTest {
    @Mock
    DeviceTypesDAO deviceTypesDAO;

    @InjectMocks
    DeviceTypesDataServiceImpl deviceTypesDataService;

    private DeviceTypes types;
    private DeviceTypes types1;

    @Before
    public void setUp(){
        deviceTypesDataService = new DeviceTypesDataServiceImpl();
        MockitoAnnotations.initMocks(this);

        types = new DeviceTypes();
        types.setId(1);
        types.setPortsNumber(24);
        types.setDeviceType("D-Link");

        types1 = new DeviceTypes();
        types1.setId(2);
        types1.setPortsNumber(16);
        types1.setDeviceType("Cisco");
    }

    @After
    public void tearDown(){
        types = null;
        deviceTypesDataService = null;
        types1 = null;
    }

    @Test
    public void getDeviceTypesTest(){
        List<DeviceTypes> deviceTypes = new ArrayList<>();
        deviceTypes.add(types);
        when(deviceTypesDAO.getDeviceTypes()).thenReturn(deviceTypes);

        DeviceTypesForm form = new DeviceTypesForm();
        form.setId(1);
        form.setPortsNumber(24);
        form.setDeviceType("D-Link");

        List<DeviceTypesForm> actualList = deviceTypesDataService.getDeviceTypes();
        assertTrue(actualList.contains(form));
    }

    @Test
    public void addDeviceTypeTest(){
        when(deviceTypesDAO.create(types)).thenReturn(0);

        DeviceTypesForm form = new DeviceTypesForm();
        form.setId(1);
        form.setPortsNumber(24);
        form.setDeviceType("D-Link");

        int id = deviceTypesDataService.addDeviceType(form);
        assertEquals(id, 0);
    }

    @Test
    public void getByIdTest(){
        when(deviceTypesDAO.getById(1)).thenReturn(types);
        DeviceTypesForm form = deviceTypesDataService.getById(1);
        assertEquals(form.getPortsNumber(), types.getPortsNumber());
    }

    @Test
    public void deleteDeviceTypeTest(){
        int id = deviceTypesDAO.create(types);
        deviceTypesDataService.deleteDeviceType(id);
        when(deviceTypesDAO.getById(id)).thenReturn(types);
        DeviceTypesForm form = deviceTypesDataService.getById(id);
        assertEquals(form.getPortsNumber(), types.getPortsNumber());
    }

}
