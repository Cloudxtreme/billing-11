package com.elstele.bill.test.controller;


import com.elstele.bill.controller.ServiceTypeController;
import com.elstele.bill.datasrv.interfaces.ServiceTypeDataService;
import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.form.ServiceInternetAttributeForm;
import com.elstele.bill.form.ServiceTypeForm;
import com.elstele.bill.Builders.bean.ServiceTypeBuilder;
import com.elstele.bill.Builders.form.ServiceTypeFormBuilder;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Messagei18nHelper;
import com.elstele.bill.validator.ServiceAttributeValidator;
import com.elstele.bill.validator.ServiceTypeValidator;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:test-servlet-context.xml")
public class ServiceTypeControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private MockHttpSession mockSession;

    @InjectMocks
    ServiceTypeController controller;

    @Mock
    ServiceTypeDataService serviceTypeDataService;
    @Mock
    ServiceTypeValidator serviceTypeValidator;
    @Mock
    ServiceAttributeValidator serviceAttributeValidator;
    @Mock
    Messagei18nHelper messagei18nHelper;

    private List<ServiceType> serviceTypeList;
    private List<ServiceTypeForm> serviceTypeFormList;
    private List<ServiceInternetAttributeForm> serviceInternetAttributeFormList;
    private List<Constants.AccountType> bussTypes;
    private ServiceTypeForm serviceTypeForm;
    private ServiceInternetAttributeForm serviceInternetAttributeForm;
    private ServiceType serviceType;
    private LocalUser user;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());

        ServiceTypeBuilder builder = new ServiceTypeBuilder();
        serviceType = builder.build().withId(1).withName("test").withPrice(12f).getRes();
        serviceTypeList = new ArrayList<>();
        serviceTypeList.add(serviceType);

        bussTypes = new ArrayList<Constants.AccountType>(Arrays.asList(Constants.AccountType.values()));

        ServiceTypeFormBuilder serviceTypeFormBuilder = new ServiceTypeFormBuilder();
        serviceTypeForm = serviceTypeFormBuilder.build().withId(1).withName("test").getRes();
        serviceTypeFormList = new ArrayList<>();
        serviceTypeFormList.add(serviceTypeForm);

        serviceInternetAttributeForm = new ServiceInternetAttributeForm();
        serviceInternetAttributeFormList = new ArrayList<>();
        serviceInternetAttributeFormList.add(serviceInternetAttributeForm);

        user = new LocalUser();
        user.setUsername("test");
    }

    @Test
    public void serviceDeleteTest() throws Exception {
        when(serviceTypeDataService.listServiceType()).thenReturn(serviceTypeList);
        this.mockMvc.perform(get("/serviceType/{id}/delete", 1)
                .session(mockSession)
                .sessionAttr(Constants.LOCAL_USER, user)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("serviceType"))
                .andExpect(model().attribute("serviceTypeList", serviceTypeList));

    }

    @Test
    public void serviceUpdateTest() throws Exception {
        when(serviceTypeDataService.getServiceTypeFormById(1)).thenReturn(serviceTypeForm);
        this.mockMvc.perform(get("/serviceType/{id}/update", 1)
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("serviceType_form"))
                .andExpect(model().attribute("serviceForm", serviceTypeForm))
                .andExpect(model().attribute("bussTypes", bussTypes));
    }


    @Test
    public void serviceTypeAddTest() throws Exception {
        when(serviceTypeDataService.listServiceType()).thenReturn(serviceTypeList);
        this.mockMvc.perform(get("/serviceType/form")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("serviceType_form"));
    }

    @Test
    public void serviceAddTest() throws Exception {
        when(serviceTypeDataService.saveServiceType(serviceTypeForm, "test")).thenReturn(Constants.SUCCESS_MESSAGE);
        when(messagei18nHelper.getTypeMessage(Constants.SUCCESS_MESSAGE)).thenReturn(Constants.SUCCESS_MESSAGE);
        this.mockMvc.perform(post("/serviceType/form")
                .session(mockSession)
                .sessionAttr(Constants.LOCAL_USER, user)
                .param("id", "1")
                .param("name", "test")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("serviceType"));
    }

    @Test
    public void serviceAttributeDeleteTest() throws Exception {
        when(serviceTypeDataService.getServiceTypeFormById(1)).thenReturn(serviceTypeForm);
        when(serviceTypeDataService.listServiceAttribute(1)).thenReturn(serviceInternetAttributeFormList);
        this.mockMvc.perform(get("/serviceAttribute/{serviceId}/{serviceAttributeId}/delete", 1, 1)
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("serviceType_form"))
                .andExpect(model().attribute("serviceForm", serviceTypeForm))
                .andExpect(model().attribute("serviceInternetAttributeList", serviceInternetAttributeFormList));
    }

    @Test
    public void serviceAttributeModifyTest() throws Exception {
        when(serviceTypeDataService.listServiceAttribute(1)).thenReturn(serviceInternetAttributeFormList);
        this.mockMvc.perform(post("/serviceAttribute/modify")
                .session(mockSession)
                .param("id", "1")
                .param("serviceTypeId", "1")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("serviceType_form"))
                .andExpect(model().attribute("serviceInternetAttributeList", serviceInternetAttributeFormList));
    }

    @Test
    public void serviceAttributeModifyTestGet() throws Exception {
        when(serviceTypeDataService.getServiceAttributeForm(1, 1)).thenReturn(serviceInternetAttributeForm);
        this.mockMvc.perform(get("/serviceAttribute/{serviceId}/{serviceAttributeId}/modify", 1, 1)
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("serviceAttributeForm"))
                .andExpect(model().attribute("serviceAttributeForm", serviceInternetAttributeForm));
    }

    @Test
    public void getServiceTypeListTest() throws Exception {
        when(serviceTypeDataService.listServiceType("test")).thenReturn(serviceTypeList);
        String mapAsString = "{\"1\":\"test (12.0 грн.)\"}";
        this.mockMvc.perform(get("/serviceTypeList")
                .session(mockSession)
                .param("type", "test")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().string(mapAsString));
    }

    @Test
    public void serviceTypeListTest() throws Exception {
        when(serviceTypeDataService.listServiceType()).thenReturn(serviceTypeList);
        this.mockMvc.perform(get("/serviceType/catalog")
                .session(mockSession)
                .param("type", "test")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("serviceType"))
                .andExpect(model().attribute("serviceTypeList", serviceTypeList));
    }

}
