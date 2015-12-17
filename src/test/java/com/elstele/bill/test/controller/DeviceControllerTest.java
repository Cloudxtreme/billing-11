package com.elstele.bill.test.controller;

import com.elstele.bill.controller.DeviceController;
import com.elstele.bill.datasrv.interfaces.DeviceDataService;
import com.elstele.bill.datasrv.interfaces.DeviceTypesDataService;
import com.elstele.bill.datasrv.interfaces.IpDataService;
import com.elstele.bill.datasrv.interfaces.IpSubnetDataService;
import com.elstele.bill.domain.DeviceTypes;
import com.elstele.bill.domain.IpSubnet;
import com.elstele.bill.form.*;
import com.elstele.bill.test.builder.form.DeviceFormBuilder;
import com.elstele.bill.test.builder.form.DeviceTypeFormBuilder;
import com.elstele.bill.test.builder.form.IpFormBuilder;
import com.elstele.bill.test.builder.form.IpSubnetFormBuilder;
import com.elstele.bill.utils.Enums.SubnetPurpose;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:test-servlet-context.xml")
public class DeviceControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private MockHttpSession mockSession;

    @Mock
    DeviceDataService deviceDataService;
    @Mock
    DeviceTypesDataService deviceTypesDataService;
    @Mock
    IpDataService ipDataService;
    @Mock
    IpSubnetDataService ipSubnetDataService;

    @InjectMocks
    DeviceController deviceController;

    private DeviceFormBuilder deviceFormBuilder;
    private DeviceTypeFormBuilder deviceTypeFormBuilder;
    private IpFormBuilder ipFormBuilder;
    private IpSubnetFormBuilder ipSubnetFormBuilder;

    private DeviceForm deviceForm;
    private DeviceForm deviceForm1;
    private List<DeviceForm> expectedList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(deviceController).build();
        mockSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());

        deviceFormBuilder = new DeviceFormBuilder();
        deviceTypeFormBuilder = new DeviceTypeFormBuilder();
        ipFormBuilder = new IpFormBuilder();
        ipSubnetFormBuilder = new IpSubnetFormBuilder();

        deviceForm = deviceFormBuilder.build()
                .withDeviceTypeForm(new DeviceTypesForm())
                .withAddressForm(new AddressForm())
                .withIpForm(new IpForm())
                .withId(1)
                .withName("form").getRes();

        deviceForm1 = deviceFormBuilder.build()
                .withDeviceTypeForm(new DeviceTypesForm())
                .withAddressForm(new AddressForm())
                .withIpForm(new IpForm())
                .withId(2)
                .withName("form1").getRes();

        expectedList = new ArrayList<>();
        expectedList.add(deviceForm);
        expectedList.add(deviceForm1);
    }

    @Test
    public void getDeviceListTest() throws Exception {
        when(deviceDataService.getDevices()).thenReturn(expectedList);

        MvcResult result = this.mockMvc.perform(get("/device")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("deviceModel"))
                .andReturn();
        ModelAndView model = result.getModelAndView();
        List<DeviceForm> actual = (List<DeviceForm>) model.getModel().get("list");
        assertEquals(actual, expectedList);
    }

    @Test
    public void getDeviceTypeListTest() throws Exception {
        List<DeviceTypesForm> deviceTypesForms = new ArrayList<>();
        deviceTypesForms.add(new DeviceTypesForm());

        when(deviceTypesDataService.getDeviceTypes()).thenReturn(deviceTypesForms);

        MvcResult result = this.mockMvc.perform(get("/devicetypeslist")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("devicetypelistModel"))
                .andReturn();
        ModelAndView model = result.getModelAndView();
        List<DeviceTypesForm> actual = (List<DeviceTypesForm>) model.getModel().get("devicetypelist");
        assertEquals(actual, deviceTypesForms);
    }

    @Test
    public void addDeviceFromFormTest() throws Exception {
        List<DeviceTypesForm> deviceTypesForms = new ArrayList<>();
        DeviceTypesForm deviceTypesForm = deviceTypeFormBuilder.build().withId(1).withDeviceType("Test").getRes();
        deviceTypesForms.add(deviceTypesForm);
        when(deviceTypesDataService.getDeviceTypes()).thenReturn(deviceTypesForms);

        List<IpSubnetForm> subnetForms = new ArrayList<>();
        IpSubnetForm ipSubnetForm = ipSubnetFormBuilder.build().withSubnet("test").withId(1).getRes();
        subnetForms.add(ipSubnetForm);
        when(ipSubnetDataService.getIpSubnets()).thenReturn(subnetForms);

        List<IpForm> ipForms = new ArrayList<>();
        IpSubnet subnet = new IpSubnet();
        subnet.setSubnetPurpose(SubnetPurpose.MGMT);
        IpForm ipForm = ipFormBuilder.build().withId(1).withIpName("test").withIpSubnet(subnet).getRes();
        ipForms.add(ipForm);
        when(ipDataService.getIpAddressList()).thenReturn(ipForms);

        MvcResult result = this.mockMvc.perform(get("/adddevice")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("adddeviceModel"))
                .andReturn();
        ModelAndView model = result.getModelAndView();

        Map<Integer, String> map = (Map<Integer, String>) model.getModel().get("deviceTypesMap");
        assertTrue(map.containsKey(deviceTypesForm.getId()));
        assertTrue(map.containsValue(deviceTypesForm.getDeviceType()));

        map = (Map<Integer, String>) model.getModel().get("ipAddressList");
        assertTrue(map.containsKey(ipForm.getId()));
        assertTrue(map.containsValue(ipForm.getIpName()));

        map = (Map<Integer, String>) model.getModel().get("ipNetList");
        assertTrue(map.containsKey(ipSubnetForm.getId()));
        assertTrue(map.containsValue(ipSubnetForm.getIpSubnet()));
    }

    @Test
    @Ignore
    public void addOrUpdateDeviceFromFormTest() throws Exception {
        when(deviceDataService.addDevice(deviceForm)).thenReturn(1);
        this.mockMvc.perform(post("/adddevice")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(flash().attribute("successMessage", "Device was successfully added."))
                .andReturn();

    }
}
