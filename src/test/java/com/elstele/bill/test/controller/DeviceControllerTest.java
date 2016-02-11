package com.elstele.bill.test.controller;

import com.elstele.bill.controller.DeviceController;
import com.elstele.bill.datasrv.interfaces.DeviceDataService;
import com.elstele.bill.datasrv.interfaces.DeviceTypesDataService;
import com.elstele.bill.datasrv.interfaces.IpDataService;
import com.elstele.bill.datasrv.interfaces.IpSubnetDataService;
import com.elstele.bill.domain.IpSubnet;
import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.form.*;
import com.elstele.bill.Builders.form.DeviceFormBuilder;
import com.elstele.bill.Builders.form.DeviceTypeFormBuilder;
import com.elstele.bill.Builders.form.IpFormBuilder;
import com.elstele.bill.Builders.form.IpSubnetFormBuilder;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.utils.Enums.SubnetPurpose;
import com.elstele.bill.utils.Messagei18nHelper;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    @Mock
    Messagei18nHelper messagei18nHelper;

    @InjectMocks
    DeviceController deviceController;

    private DeviceFormBuilder deviceFormBuilder;
    private DeviceTypeFormBuilder deviceTypeFormBuilder;
    private IpFormBuilder ipFormBuilder;
    private IpSubnetFormBuilder ipSubnetFormBuilder;

    private DeviceForm deviceForm;
    private DeviceForm deviceForm1;
    private DeviceTypesForm deviceTypesForm;
    private IpSubnetForm ipSubnetForm;
    private IpForm ipForm;
    private List<DeviceForm> expectedList;
    private List<IpForm> ipForms;
    private List<IpSubnetForm> subnetForms;
    private List<DeviceTypesForm> deviceTypesForms;
    private HashMap<Integer, String> mapDevType;
    private HashMap<Integer, String> mapIp;
    private HashMap<Integer, String> mapIpSubnet;

    private LocalUser user;


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
                .withIpForm(ipFormBuilder.build().withId(1).getRes())
                .withId(1)
                .withName("form").getRes();

        deviceForm1 = deviceFormBuilder.build()
                .withDeviceTypeForm(new DeviceTypesForm())
                .withAddressForm(new AddressForm())
                .withIpForm(new IpForm())
                .withId(2)
                .withName("form1").getRes();

        deviceTypesForms = new ArrayList<>();
        deviceTypesForm = deviceTypeFormBuilder.build().withId(1).withDeviceType("Test").getRes();
        deviceTypesForms.add(deviceTypesForm);
        deviceTypesForms.add(new DeviceTypesForm());

        subnetForms = new ArrayList<>();
        ipSubnetForm = ipSubnetFormBuilder.build().withSubnet("test").withId(1).getRes();
        subnetForms.add(ipSubnetForm);

        ipForms = new ArrayList<>();
        IpSubnet subnet = new IpSubnet();
        subnet.setSubnetPurpose(SubnetPurpose.MGMT);
        ipForm = ipFormBuilder.build().withId(1).withIpName("test").withIpSubnet(subnet).getRes();
        ipForms.add(ipForm);

        expectedList = new ArrayList<>();
        expectedList.add(deviceForm);
        expectedList.add(deviceForm1);


        mapDevType = new HashMap();
        mapDevType.put(deviceTypesForm.getId(), deviceTypesForm.getDeviceType());
        mapIp = new HashMap();
        mapIp.put(ipForm.getId(), ipForm.getIpName());
        mapIpSubnet = new HashMap();
        mapIpSubnet.put(ipSubnetForm.getId(), ipSubnetForm.getIpSubnet());

        user = new LocalUser();
        user.setUsername("test");;
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
        when(deviceTypesDataService.getDeviceTypesAsMap()).thenReturn(mapDevType);
        when(ipSubnetDataService.getIpSubnetsAsMap()).thenReturn(mapIpSubnet);
        when(ipDataService.getIpAddressAsMap()).thenReturn(mapIp);

        MvcResult result = this.mockMvc.perform(get("/adddevice")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("adddeviceModel"))
                .andReturn();
        ModelAndView model = result.getModelAndView();

        HashMap<Integer, String> map = (HashMap<Integer, String>) model.getModel().get("deviceTypesMap");
        assertEquals(map, mapDevType);

        map = (HashMap<Integer, String>) model.getModel().get("ipAddressList");
        assertEquals(map, mapIp);

        map = (HashMap<Integer, String>) model.getModel().get("ipNetList");
        assertEquals(map, mapIpSubnet);
    }

    @Test
    public void addOrUpdateDeviceFromFormTest() throws Exception {
        DeviceForm deviceFormExpected = deviceFormBuilder.build().withIpForm(ipFormBuilder.build().withId(1).getRes()).getRes();
        when(deviceDataService.addDevice(deviceFormExpected, "test")).thenReturn(1);
        when(messagei18nHelper.getMessage(Constants.DEVICE_ADD_SUCCESS)).thenReturn("Device was successfully added.");

        this.mockMvc.perform(post("/adddevice")
                .session(mockSession)
                .sessionAttr("deviceForm", deviceFormExpected)
                .sessionAttr(Constants.LOCAL_USER, user)
                .accept(MediaType.ALL))
                .andExpect(status().is(302))
                .andExpect(flash().attribute(Constants.SUCCESS_MESSAGE, "Device was successfully added."));
    }

    @Test
    public void deleteDeviceTest() throws Exception {
        when(deviceDataService.deleteDevice(deviceForm.getId(), "test")).thenReturn(ResponseToAjax.SUCCESS);
        MvcResult result = this.mockMvc.perform(post("/device/delete")
                .session(mockSession)
                .sessionAttr(Constants.LOCAL_USER, user)
                .content(deviceForm.getId().toString())
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("SUCCESS"));
    }

    @Test
    public void editDeviceTest() throws Exception {
        when(deviceDataService.getById(1)).thenReturn(deviceForm);
        when(deviceTypesDataService.getDeviceTypesAsMap()).thenReturn(mapDevType);
        when(ipSubnetDataService.getIpSubnetsAsMap()).thenReturn(mapIpSubnet);
        when(ipDataService.getIpAddressAsMap()).thenReturn(mapIp);

        MvcResult result = this.mockMvc.perform(get("/device/{id}/update", 1)
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("adddeviceModel"))
                .andReturn();

        ModelAndView model = result.getModelAndView();

        HashMap<Integer, String> map = (HashMap<Integer, String>) model.getModel().get("deviceTypesMap");
        assertEquals(map, mapDevType);

        map = (HashMap<Integer, String>) model.getModel().get("ipAddressList");
        assertEquals(map, mapIp);

        map = (HashMap<Integer, String>) model.getModel().get("ipNetList");
        assertEquals(map, mapIpSubnet);
    }

    @Test
    public void adddevicetypeTest() throws Exception {
        this.mockMvc.perform(post("/adddevicetype")
                .session(mockSession)
                .content("1")
                .accept(MediaType.ALL))
                .andExpect(status().is(302));
    }

    @Test
    public void editdevicetypeTest() throws Exception {
        this.mockMvc.perform(post("/editdevicetype")
                .session(mockSession)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(deviceTypesForm))
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().string("\"SUCCESS\""));
    }

    @Test
    public void getValidIpstest() throws Exception {
        HashMap<Integer, String> expectedMap = new HashMap<>();
        expectedMap.put(1, "test");
        when(ipDataService.getIpMapBySubnets("1")).thenReturn(expectedMap);
        String mapInStringVariant = "{\"1\":\"test\"}";
        this.mockMvc.perform(post("/getValidIps")
                .session(mockSession)
                .content("1")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().string(mapInStringVariant));
    }

    @Test
    public void ipAddressListReturnTest() throws Exception {
        HashMap<Integer, String> expectedMap = new HashMap<>();
        expectedMap.put(1, "test");
        when(ipDataService.getIpAddressAsMap()).thenReturn(expectedMap);
        String mapInStringVariant = "{\"1\":\"test\"}";
        this.mockMvc.perform(get("/returniplist")
                .session(mockSession)
                .content("1")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().string(mapInStringVariant));
    }
}
