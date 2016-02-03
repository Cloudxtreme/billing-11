package com.elstele.bill.test.controller;

import com.elstele.bill.controller.CallsController;
import com.elstele.bill.datasrv.interfaces.CallDataService;
import com.elstele.bill.form.CallForm;
import com.elstele.bill.reportCreators.CallsRequestParamTO;
import com.elstele.bill.test.builder.bean.CallRequestParamTOBuilder;
import com.elstele.bill.test.builder.form.CallFormBuilder;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:test-servlet-context.xml")
public class CallsControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private MockHttpSession mockSession;

    @Mock
    CallDataService callDataService;

    @InjectMocks
    CallsController callsController;

    private List<CallForm> expectedList;
    private List<CallForm> expectedListWithOneValue;
    private Date startDate;
    private Date endDate;
    private CallsRequestParamTO paramTO;
    private CallsRequestParamTO paramTOForOneValue;
    CallRequestParamTOBuilder paramTOBuilder;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(callsController).build();
        mockSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());

        startDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.DATE, 1);
        endDate = c.getTime();

        expectedList = new ArrayList<>();
        CallFormBuilder callFormBuilder = new CallFormBuilder();

        CallForm callForm = callFormBuilder.build().withId(1).withNumberA("1234556").withNumberB("2212133").withStartTime(startDate).getRes();
        CallForm callForm1 = callFormBuilder.build().withId(2).withNumberA("1444222").withNumberB("1111222").withStartTime(startDate).getRes();
        expectedList.add(callForm);
        expectedList.add(callForm1);

        paramTOBuilder = new CallRequestParamTOBuilder();
        paramTO = paramTOBuilder.build()
                .withRows(10)
                .withPage(0)
                .withNumberA("")
                .withNumberB("")
                .withSelectedTime("")
                .getRes();
        paramTOForOneValue = paramTOBuilder.build()
                .withRows(10)
                .withPage(0)
                .withNumberA("123")
                .withNumberB("221")
                .withSelectedTime("")
                .getRes();
        expectedListWithOneValue = new ArrayList<>();
        expectedListWithOneValue.add(callForm);
    }

    @Test
    public void getAccountsListSearchTest() throws Exception {
        when(callDataService.getCallsList(paramTO)).thenReturn(expectedList);

        MvcResult resultWithoutSearchValues = this.mockMvc.perform(get("/callsList")
                .session(mockSession)
                .param("rows", "10")
                .param("page", "0")
                .param("numberA", "")
                .param("numberB", "")
                .param("timeRange", "")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();

        String content = resultWithoutSearchValues.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<CallForm> list = mapper.readValue(content, mapper.getTypeFactory().constructCollectionType(
                ArrayList.class, CallForm.class));

        assertTrue(expectedList.equals(list));

        when(callDataService.getCallsList(paramTOForOneValue)).thenReturn(expectedListWithOneValue);

        MvcResult resultAnySearchValues = this.mockMvc.perform(get("/callsList")
                .session(mockSession)
                .param("rows", "10")
                .param("page", "0")
                .param("numberA", "123")
                .param("numberB", "221")
                .param("timeRange", "")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();

        String contentElse = resultAnySearchValues.getResponse().getContentAsString();
        ArrayList<CallForm> list1 = mapper.readValue(contentElse, mapper.getTypeFactory().constructCollectionType(
                ArrayList.class, CallForm.class));

        assertTrue(expectedListWithOneValue.equals(list1));
    }
    @Test
    public void handleCallsHomeTest() throws Exception {
        this.mockMvc.perform(get("/callshome")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("calls_list"))
                .andExpect(forwardedUrl("calls_list"));
    }

    @Test
    public void handleCallsHomeOnChangeTest() throws Exception {
        CallsRequestParamTO paramEmtpy = paramTOBuilder.build()
                .withNumberA("")
                .withNumberB("")
                .withStartDate("")
                .withPageResult(10)
                .getRes();
        CallsRequestParamTO paramFull= paramTOBuilder.build()
                .withNumberA("2222222")
                .withNumberB("")
                .withStartDate("")
                .withPageResult(2)
                .getRes();

        when(callDataService.determineTotalPagesForOutput(paramEmtpy)).thenReturn(10);
        when(callDataService.determineTotalPagesForOutput(paramFull)).thenReturn(1);

        MvcResult pageCountsWithoutValues = this.mockMvc.perform(post("/callsPages")
                .session(mockSession)
                .param("pageResults", "10")
                .param("numberA", "")
                .param("numberB", "")
                .param("timeRange", "")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();
        String contentElse = pageCountsWithoutValues.getResponse().getContentAsString();
        assertTrue(contentElse.equals("10"));

        MvcResult pageCountsAnyValues = this.mockMvc.perform(post("/callsPages")
                .session(mockSession)
                .param("pageResults", "2")
                .param("numberA", "2222222")
                .param("numberB", "")
                .param("timeRange", "")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();
        String contentElseWithSearch = pageCountsAnyValues.getResponse().getContentAsString();
        assertTrue(contentElseWithSearch.equals("1"));
    }
}
