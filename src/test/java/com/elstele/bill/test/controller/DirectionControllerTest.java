package com.elstele.bill.test.controller;

import com.elstele.bill.controller.DirectionController;
import com.elstele.bill.datasrv.interfaces.DirectionDataService;
import com.elstele.bill.datasrv.interfaces.TariffZoneDataService;
import com.elstele.bill.form.DirectionForm;
import com.elstele.bill.form.TariffZoneForm;
import com.elstele.bill.test.builder.form.DirectionFormBuilder;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.utils.Messagei18nHelper;
import com.elstele.bill.validator.DirectionValidator;
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

import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:test-servlet-context.xml")
public class DirectionControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private MockHttpSession mockSession;

    @Mock
    DirectionDataService directionDataService;
    @Mock
    TariffZoneDataService tariffZoneDataService;
    @Mock
    DirectionValidator validator;
    @Mock
    Messagei18nHelper messagei18nHelper;

    @InjectMocks
    DirectionController controller;

    private DirectionForm form;
    private Date validFrom;
    private Date validTo;

    private final String DIRECTION_PREFIX = "097";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());

        Calendar cal = Calendar.getInstance();
        cal.set(2015, Calendar.JULY, 5, 0, 0, 0);
        validFrom = cal.getTime();
        cal.set(Calendar.YEAR, -1);
        validTo = cal.getTime();

        form = new DirectionFormBuilder().build()
                .withDescription("TestDesc")
                .withId(1)
                .withPrefix("097")
                .withValidFromDate(validFrom.getTime())
                .withValidToDate(validTo.getTime())
                .getRes();
    }

    @Test
    public void directionListHomeTest() throws Exception {

        when(directionDataService.getPagesCount(10, "")).thenReturn(100);

        this.mockMvc.perform(get("/direction/home")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("directionsModel"))
                .andExpect(model().attribute("pageNum", 1))
                .andExpect(model().attribute("pagesTotal", 100));
    }

    @Test
    public void getDirectionListTest() throws Exception {
        List<DirectionForm> expectedList = new ArrayList<>();
        expectedList.add(form);

        when(directionDataService.getDirectionList(1, 10, DIRECTION_PREFIX)).thenReturn(expectedList);

        MvcResult result = this.mockMvc.perform(get("/direction/list")
                .session(mockSession)
                .param("rows", "10")
                .param("page", "1")
                .param("value", DIRECTION_PREFIX)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<DirectionForm> actual = mapper.readValue(content, mapper.getTypeFactory().
                constructCollectionType(ArrayList.class, DirectionForm.class));

        assertTrue(actual.equals(expectedList));
    }

    @Test
    public void getPagesCountTest() throws Exception {
        when(directionDataService.getPagesCount(25, DIRECTION_PREFIX)).thenReturn(100);

        this.mockMvc.perform(post("/direction/count")
                .session(mockSession)
                .accept(MediaType.ALL)
                .param("pageResults", "25")
                .param("value", DIRECTION_PREFIX))
                .andExpect(status().isOk())
                .andExpect(content().string("100"));
    }

    @Test
    public void deleteDirectionTest() throws Exception {
        when(directionDataService.deleteDirection(1)).thenReturn(Constants.DIRECTION_DELETE_SUCCESS);
        when(messagei18nHelper.getTypeMessage(Constants.DIRECTION_DELETE_SUCCESS)).thenReturn(Constants.SUCCESS_MESSAGE);
        when(messagei18nHelper.getMessage(Constants.DIRECTION_DELETE_SUCCESS)).thenReturn("Direction was successfully deleted.");

        this.mockMvc.perform(get("/direction/delete/{id}", 1)
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().is(302))
                .andExpect(flash().attribute(Constants.SUCCESS_MESSAGE, "Direction was successfully deleted."));
    }

    @Test
    public void addDirectionFormTest() throws Exception {

        this.mockMvc.perform(post("/direction/add")
                .session(mockSession)
                .sessionAttr("directionForm", form)
                .accept(MediaType.ALL))
                .andExpect(status().isOk());
    }

    @Test
    public void getDirectionForEditTest() throws Exception {
        when(directionDataService.getDirectionById(1)).thenReturn(form);
        MvcResult result = this.mockMvc.perform(get("/direction/edit")
                .session(mockSession)
                .param("id", "1")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        DirectionForm actual = mapper.readValue(content, mapper.getTypeFactory().constructType(DirectionForm.class));

        assertTrue(actual.equals(form));
    }

    @Test
    public void editDirectionPost() throws Exception {
        when(directionDataService.getDirectionById(1)).thenReturn(form);
        this.mockMvc.perform(post("/direction/edit")
                .session(mockSession)
                .sessionAttr("directionForm", form)
                .accept(MediaType.ALL))
                .andExpect(status().isOk());
    }

    @Test
    public void checkFreeTest() throws Exception {
        Long dateValue = validFrom.getTime();
        when(directionDataService.checkForFree(1, DIRECTION_PREFIX, dateValue)).thenReturn(ResponseToAjax.FREE);
        MvcResult result = this.mockMvc.perform(get("/direction/checkfree")
                .session(mockSession)
                .param("id", "1")
                .param("prefix", DIRECTION_PREFIX)
                .param("validFrom", Long.toString(dateValue))
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains(ResponseToAjax.FREE.toString()));
    }

    @Test
    public void getTarZonesListTest() throws Exception {
        List<TariffZoneForm> allZoneList = new ArrayList<>();
        TariffZoneForm form1 = new TariffZoneForm();
        form1.setId(1);
        form1.setZoneName("Test1");
        TariffZoneForm form2 = new TariffZoneForm();
        form2.setId(2);
        form2.setZoneName("Test2");

        allZoneList.add(form1);
        allZoneList.add(form2);

        List<TariffZoneForm> actualZoneList = new ArrayList<>();
        actualZoneList.add(form1);

        when(tariffZoneDataService.getTariffZonesList()).thenReturn(allZoneList);
        MvcResult result = this.mockMvc.perform(get("/direction/getAllZones")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<DirectionForm> actual = mapper.readValue(content, mapper.getTypeFactory().
                constructCollectionType(ArrayList.class, TariffZoneForm.class));

        assertTrue(actual.equals(allZoneList));

        when(tariffZoneDataService.getOnlyActualTariffZoneList()).thenReturn(actualZoneList);
        result = this.mockMvc.perform(get("/direction/getActualZones")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();
        content = result.getResponse().getContentAsString();
        actual = mapper.readValue(content, mapper.getTypeFactory().
                constructCollectionType(ArrayList.class, TariffZoneForm.class));

        assertTrue(actual.equals(actualZoneList));
    }
}
