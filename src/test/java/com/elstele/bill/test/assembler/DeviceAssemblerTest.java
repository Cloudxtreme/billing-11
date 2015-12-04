package com.elstele.bill.test.assembler;

import com.elstele.bill.assembler.DeviceAssembler;
import com.elstele.bill.dao.interfaces.DeviceDAO;
import com.elstele.bill.dao.interfaces.DeviceTypesDAO;
import com.elstele.bill.dao.interfaces.IpDAO;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.domain.Device;
import com.elstele.bill.domain.DeviceTypes;
import com.elstele.bill.domain.Ip;
import com.elstele.bill.form.DeviceForm;
import com.elstele.bill.form.DeviceTypesForm;
import com.elstele.bill.form.IpForm;
import com.elstele.bill.utils.Enums.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
public class DeviceAssemblerTest {
    @Mock
    DeviceDAO deviceDAO;
    @Mock
    DeviceTypesDAO deviceTypesDAO;
    @Mock
    IpDAO ipDAO;
    @InjectMocks
    private DeviceAssembler deviceAssembler;

    private Device device;
    private DeviceForm deviceForm;
    private DeviceTypes deviceTypes;
    private Ip ip;

    @Before
    public void setUp() {
        deviceAssembler = new DeviceAssembler(deviceTypesDAO, ipDAO);
        MockitoAnnotations.initMocks(this);

        device = new Device();
        device.setId(1);

        ip = new Ip();
        ip.setId(10);
        deviceTypes = new DeviceTypes();
        deviceTypes.setId(5);
        device.setIpAdd(ip);
        device.setDeviceTypes(deviceTypes);
        device.setStatus(Status.ACTIVE);

        deviceForm = new DeviceForm();
        deviceForm.setId(1);
        DeviceTypesForm deviceTypesForm = new DeviceTypesForm();
        deviceTypesForm.setId(5);
        IpForm ipForm = new IpForm();
        ipForm.setId(10);
        deviceForm.setIpForm(ipForm);
        deviceForm.setDevType(deviceTypesForm);
        deviceForm.setStatus(Status.ACTIVE);
    }

    @Test
    public void fromBeanToFormTest(){
        DeviceForm actual = deviceAssembler.fromBeanToForm(device);
        assertTrue(actual.equals(deviceForm));
    }

    @Test
    public void fromFormToBeanTest(){
        when(ipDAO.getById(10)).thenReturn(ip);
        when(deviceTypesDAO.getById(5)).thenReturn(deviceTypes);
        Device actual = deviceAssembler.fromFormToBean(deviceForm);
        assertTrue(actual.equals(device));
    }
}
