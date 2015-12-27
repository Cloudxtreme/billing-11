package com.elstele.bill.test.controller;

import com.elstele.bill.controller.ServiceController;
import com.elstele.bill.datasrv.interfaces.*;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.form.*;
import com.elstele.bill.test.builder.bean.DeviceBuilder;
import com.elstele.bill.test.builder.bean.ServiceTypeBuilder;
import com.elstele.bill.test.builder.form.*;
import com.elstele.bill.utils.Constants;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:test-servlet-context.xml")
public class ServiceControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private MockHttpSession mockSession;

    @InjectMocks
    ServiceController controllerUnderTest;

    @Mock
    ServiceDataService serviceDataService;
    @Mock
    AccountDataService accountDataService;
    @Mock
    TransactionDataService transactionDataService;
    @Mock
    ServiceTypeDataService serviceTypeDataService;
    @Mock
    IpSubnetDataService ipSubnetDataService;
    @Mock
    DeviceDataService deviceDataService;
    @Mock
    IpDataService ipDataService;

    private AccountForm accountForm;
    private ServiceForm serviceForm;
    private ServiceTypeForm serviceTypeForm;

    private List<ServiceType> serviceTypeList;
    private List<DeviceForm> deviceFormList;
    private List<IpSubnetForm> ipSubnetFormList;
    private List<IpForm> ipFormList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();
        mockSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());

        AccountFormBuilder accountFormBuilder = new AccountFormBuilder();
        accountForm = accountFormBuilder.build().withId(1).withAccName("test").withAccType(Constants.AccountType.LEGAL).withBalance(11f).withFIO("test").getRes();

        ServiceTypeFormBuilder serviceTypeFormBuilder = new ServiceTypeFormBuilder();
        serviceTypeForm = serviceTypeFormBuilder.build().withServiceType("test").withId(2).getRes();

        ServiceInternetFormBuilder serviceInternetFormBuilder = new ServiceInternetFormBuilder();
        serviceForm = serviceInternetFormBuilder.build().withId(1).withPassword("1111").withUsername("test").withServiceType(serviceTypeForm).withAccount(accountForm.getId()).getRes();

        ServiceTypeBuilder serviceTypeBuilder = new ServiceTypeBuilder();
        serviceTypeList = new ArrayList<>();
        ServiceType serviceType = serviceTypeBuilder.build().withServiceType("test").withDescription("ss").withId(1).getRes();
        serviceTypeList.add(serviceType);

        DeviceFormBuilder deviceFormBuilder = new DeviceFormBuilder();
        deviceFormList = new ArrayList<>();
        DeviceForm deviceForm = deviceFormBuilder.build().withId(1).getRes();
        deviceFormList.add(deviceForm);

        ipSubnetFormList = new ArrayList<>();
        IpSubnetFormBuilder ipSubnetFormBuilder = new IpSubnetFormBuilder();
        IpSubnetForm ipSubnetForm = ipSubnetFormBuilder.build().withId(100).getRes();
        ipSubnetFormList.add(ipSubnetForm);

        ipFormList = new ArrayList<>();
        IpFormBuilder ipFormBuilder = new IpFormBuilder();
        IpForm ipForm = ipFormBuilder.build().withIpName("tea").withId(1).getRes();
        ipFormList.add(ipForm);
    }

    @Test
    public void serviceDeleteTest() throws Exception {
        when(accountDataService.getAccountById(1)).thenReturn(accountForm);
        MvcResult result = this.mockMvc.perform(get("/service/account/{accountId}/{accountServiceId}/delete", 1, 2)
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("accountFull"))
                .andReturn();
        AccountForm actual = (AccountForm) result.getModelAndView().getModel().get("accountForm");
        assertEquals(actual, accountForm);
    }

    @Test
    public void accountServiceModify() throws Exception {
        when(accountDataService.getAccountById(accountForm.getId())).thenReturn(accountForm);
        when(serviceTypeDataService.listServiceType()).thenReturn(serviceTypeList);
        when(deviceDataService.getDevices()).thenReturn(deviceFormList);
        when(ipSubnetDataService.getIpSubnets()).thenReturn(ipSubnetFormList);

        this.mockMvc.perform(post("/service/account/form")
                .session(mockSession)
                .param("accountId", "1")
                .param("serviceInternet.ip.id", "1")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("/account_service_form"))
                .andExpect(model().attribute("errorClass", "text-danger"));
    }

    @Test
    public void ipAddressAddBySubnetTest() throws Exception {
        when(serviceDataService.getCurrentIpAddressByServiceFormId(1)).thenReturn(1);
        when(ipDataService.getBySubnetId(1)).thenReturn(ipFormList);

        this.mockMvc.perform(post("/getIpAddressList/{idObj}", 1)
                .session(mockSession)
                .content("1")
                .accept(MediaType.ALL))
                .andExpect(status().isOk());

    }

    @Test
    public void changeSoftBlockStatusTest() throws Exception {
        this.mockMvc.perform(get("/changeSoftBlockStatus")
                .session(mockSession)
                .param("serviceId", "1")
                .accept(MediaType.ALL))
                .andExpect(status().isOk());
    }

    @Test
    public void deviceFreePortListTest() throws Exception {
        List<Integer> integers = new ArrayList<>();
        integers.add(1);

        when(serviceDataService.addCurrentDevicePortToList(integers, 1, 1)).thenReturn(integers);
        when(deviceDataService.getDeviceFreePorts(1)).thenReturn(integers);

        this.mockMvc.perform(post("/getDeviceFreePortList/{idObj}", 1)
                .session(mockSession)
                .content("1")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().string(integers.toString()));
    }

    @Test
    public void serviceModifyTest() throws Exception {
        when(serviceDataService.getServiceFormById(1)).thenReturn(serviceForm);
        when(accountDataService.getAccountById(1)).thenReturn(accountForm);

        List<Constants.Period> period = new ArrayList<Constants.Period>(Arrays.asList(Constants.Period.values()));
        this.mockMvc.perform(get("/service/account/{accountId}/{accountServiceId}/modify", 1, 1)
                .session(mockSession)
                .content("1")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("account_service_form"))
                .andExpect(model().attribute("account", accountForm))
                .andExpect(model().attribute("servicePeriodList", period))
                .andExpect(model().attribute("serviceForm", serviceForm));
    }

    @Test
    public void getCurrentIpAddressTest() throws Exception {
        when(serviceDataService.getCurrentIpAddressByServiceFormId(1)).thenReturn(1);
        this.mockMvc.perform(get("/getCurrentIpAddress")
                .session(mockSession)
                .param("serviceId", "1")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void serviceListTest() throws Exception {
        List<AccountForm> accList = new ArrayList<>();
        accList.add(accountForm);

        when(accountDataService.getAccountsList()).thenReturn(accList);
        this.mockMvc.perform(get("/service/account/")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("account_service"))
                .andExpect(model().attribute("accountList", accList));
    }
}
