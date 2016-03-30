package com.elstele.bill.test.controller;

import com.elstele.bill.controller.TariffZoneController;
import com.elstele.bill.datasrv.interfaces.PreferenceRuleDataService;
import com.elstele.bill.datasrv.interfaces.TariffZoneDataService;
import com.elstele.bill.form.PreferenceRuleForm;
import com.elstele.bill.form.TariffZoneForm;
import com.elstele.bill.test.builder.form.TariffZoneFormBuilder;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Messagei18nHelper;
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
public class TariffZoneControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private MockHttpSession mockSession;

    @Mock
    TariffZoneDataService dataService;
    @Mock
    PreferenceRuleDataService preferenceRuleDataService;
    @Mock
    Messagei18nHelper helper;
    @InjectMocks
    TariffZoneController controller;

    private TariffZoneForm tariffZoneForm;
    private TariffZoneForm tariffZoneForm1;
    private List<TariffZoneForm> tariffZoneFormList;

    private Date validFrom;
    private Date validTo;

    private List<PreferenceRuleForm> preferenceRuleList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());

        Calendar cal = Calendar.getInstance();
        cal.set(2015, Calendar.JULY, 22, 0, 0, 0);
        validFrom = cal.getTime();
        cal.add(Calendar.YEAR, +1);
        validTo = cal.getTime();

        tariffZoneForm = new TariffZoneFormBuilder().build()
                .withId(1)
                .withZoneId(10)
                .withTariff(10.3f)
                .withDollar(true)
                .withPrefProfileId(2)
                .withValidFrom(validFrom.getTime())
                .withValidTo(validTo.getTime())
                .withZoneName("testZone")
                .getRes();

        tariffZoneForm1 = new TariffZoneFormBuilder().build()
                .withId(2)
                .withZoneId(30)
                .withTariff(110.3f)
                .withDollar(false)
                .withPrefProfileId(3)
                .withValidFrom(validFrom.getTime())
                .withValidTo(validTo.getTime())
                .withZoneName("testZoNE1")
                .getRes();

        tariffZoneFormList = new ArrayList<>();
        tariffZoneFormList.add(tariffZoneForm);
        tariffZoneFormList.add(tariffZoneForm1);

        preferenceRuleList = new ArrayList<>();
        PreferenceRuleForm rule = new PreferenceRuleForm();
        rule.setId(10);
        rule.setProfileId(22);
        rule.setTarif(98.1f);
        PreferenceRuleForm rule1 = new PreferenceRuleForm();
        rule1.setId(22);
        rule1.setProfileId(33);
        rule1.setTarif(13f);
        preferenceRuleList.add(rule);
        preferenceRuleList.add(rule1);
    }

    @Test
    public void tariffZoneListHomeTest() throws Exception {
        List<TariffZoneForm> actualZoneList = new ArrayList<>();
        actualZoneList.add(tariffZoneForm);
        when(dataService.getOnlyActualTariffZoneList()).thenReturn(actualZoneList);
        when(dataService.getTariffZonesList()).thenReturn(tariffZoneFormList);
        when(preferenceRuleDataService.getRuleList()).thenReturn(preferenceRuleList);

        this.mockMvc.perform(get("/tariffzone/home")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("tariffZoneModel"))
                .andExpect(model().attribute("tariffzoneList", actualZoneList))
                .andExpect(model().attribute("prefProfileList", preferenceRuleList));

        this.mockMvc.perform(get("/tariffzone/all")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("tariffZoneModel"))
                .andExpect(model().attribute("tariffzoneList", tariffZoneFormList))
                .andExpect(model().attribute("prefProfileList", preferenceRuleList));
    }

    @Test
    public void addTariffZoneTest() throws Exception {
        this.mockMvc.perform(post("/tariffzone/add")
                .session(mockSession)
                .sessionAttr("tariffZoneForm", tariffZoneForm)
                .accept(MediaType.ALL))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTariffZoneTest() throws Exception {
        when(dataService.deleteZone(1)).thenReturn(Constants.ZONE_DELETED_SUCCESS);
        when(helper.getTypeMessage(Constants.ZONE_DELETED_SUCCESS)).thenReturn(Constants.SUCCESS_MESSAGE);
        when(helper.getMessage(Constants.ZONE_DELETED_SUCCESS)).thenReturn("success");

        this.mockMvc.perform(get("/tariffzone/delete/{id}", 1)
                .session(mockSession)
                .sessionAttr("tariffZoneForm", tariffZoneForm)
                .accept(MediaType.ALL))
                .andExpect(status().is(302))
                .andExpect(flash().attribute(Constants.SUCCESS_MESSAGE, "success"));
    }

    @Test
    public void getZoneForEditTest() throws Exception{
        when(dataService.getZoneById(1)).thenReturn(tariffZoneForm);

        MvcResult result = this.mockMvc.perform(get("/tariffzone/edit")
                .session(mockSession)
                .param("id", "1")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        TariffZoneForm actual = mapper.readValue(content, mapper.getTypeFactory().constructType(TariffZoneForm.class));
        assertTrue(actual.equals(tariffZoneForm));
    }

    @Test
    public void openModalWithDataAfterRedirectTest() throws Exception {
        when(dataService.getZoneById(1)).thenReturn(tariffZoneForm);
        when(dataService.getTariffZonesList()).thenReturn(tariffZoneFormList);
        when(preferenceRuleDataService.getRuleList()).thenReturn(preferenceRuleList);

        this.mockMvc.perform(get("/tariffzone/fromdirection")
                .session(mockSession)
                .param("id", "1")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("tariffZoneModel"))
                .andExpect(model().attribute("tariffZoneForm", tariffZoneForm))
                .andExpect(model().attribute("tariffzoneList", tariffZoneFormList))
                .andExpect(model().attribute("prefProfileList", preferenceRuleList));
    }

    @Test
    public void editZoneTariffPostTest() throws Exception {
        this.mockMvc.perform(post("/tariffzone/edit")
                .session(mockSession)
                .sessionAttr("tariffZoneForm", tariffZoneForm)
                .accept(MediaType.ALL))
                .andExpect(status().isOk());
    }

}
