package com.elstele.bill.test.controller;

import com.elstele.bill.controller.MonitoringController;
import com.elstele.bill.datasrv.interfaces.ServiceDataService;
import com.elstele.bill.domain.OnlineStatistic;
import com.elstele.bill.test.builder.bean.OnlineStatisticBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:test-servlet-context.xml")
public class MonitoringControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private MockHttpSession mockSession;
    private List<OnlineStatistic> statList = new ArrayList<>();

    @Mock
    ServiceDataService serviceDataService;
    @InjectMocks
    MonitoringController controllerUnderTest;



    @Before
    public void setup() {

        // this must be called for the @Mock annotations above to be processed
        // and for the mock service to be injected into the controller under
        // test.
        MockitoAnnotations.initMocks(this);

        //initialise only one controller
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();
        //this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        mockSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());

        OnlineStatisticBuilder builder = new OnlineStatisticBuilder();
        OnlineStatistic os1 = builder.build().withAllRandomFields().getRes();
        OnlineStatistic os2 = builder.build().withAllRandomFields().getRes();
        OnlineStatistic os4 = builder.build().withAllRandomFields().getRes();
        OnlineStatistic os3 = builder.build().withUsername("name").withUserfio("Snow").withNasipaddress("1.1.1.1").withNasportid("10")
                .withFramedipaddress("1.1.1.100").withAcctstarttime("10 O'clock").withAcctsessiontime(new BigInteger("100"))
                .withAcctinputoctets(new BigInteger("67876")).withAcctoutputoctets(new BigInteger("56876")).getRes();
        statList.add(os1);
        statList.add(os2);
        statList.add(os3);
        statList.add(os4);
    }

    @Test
    public void checkCount(){
        when(serviceDataService.getUsersOnline()).thenReturn(statList);
        assertTrue(statList.size() == 4);
    }

    @Test
    public void checkPresentOneOfElement(){
        //todo this test can be moved to dataServiceTest
        when(serviceDataService.getUsersOnline()).thenReturn(statList);
        OnlineStatisticBuilder builder = new OnlineStatisticBuilder();
        OnlineStatistic os = builder.build().withUsername("name").withUserfio("Snow").withNasipaddress("1.1.1.1").withNasportid("10")
                .withFramedipaddress("1.1.1.100").withAcctstarttime("10 O'clock").withAcctsessiontime(new BigInteger("100"))
                .withAcctinputoctets(new BigInteger("67876")).withAcctoutputoctets(new BigInteger("56876")).getRes();
        assertTrue(statList.contains(os));
    }

    @Test
    public void showOnlineUsrPage() throws Exception {
        this.mockMvc.perform(get("/statistic/statonline")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("statisticonline"))
                .andExpect(forwardedUrl("statisticonline"));
    }


    @Test
    public void getOnlineResults() throws Exception {
        when(serviceDataService.getUsersOnline()).thenReturn(statList);
        MvcResult result = this.mockMvc.perform(get("/getOnline")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<OnlineStatistic> list = mapper.readValue(content, mapper.getTypeFactory().constructCollectionType(
                ArrayList.class, OnlineStatistic.class));

        assertTrue(statList.equals(list));
    }

}