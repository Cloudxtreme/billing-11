package com.elstele.bill.test.controller;

import com.elstele.bill.Builders.form.AccountFormBuilder;
import com.elstele.bill.Builders.form.AuditedObjectFormBuilder;
import com.elstele.bill.controller.AuditedObjectController;
import com.elstele.bill.datasrv.interfaces.AuditedObjectDataService;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.form.AuditedObjectForm;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:test-servlet-context.xml")
public class AuditedObjectControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private MockHttpSession mockSession;

    @InjectMocks
    AuditedObjectController controller;

    @Mock
    AuditedObjectDataService dataService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());
    }

    @Test
    public void getObjectInfo() throws Exception {

        AuditedObjectFormBuilder builder = new AuditedObjectFormBuilder();

        AuditedObjectForm form = builder
                .build()
                .withChangerName("test")
                .withId(1)
                .getRes();
        AuditedObjectForm form1 = builder
                .build()
                .withChangerName("lama")
                .withId(1)
                .getRes();

        List<AuditedObjectForm> auditedObjectFormList = new ArrayList<>();
        auditedObjectFormList.add(form);
        auditedObjectFormList.add(form1);

        when(dataService.getAuditedObject(1, "Account")).thenReturn(auditedObjectFormList);
        when(dataService.getCreatedBy(auditedObjectFormList)).thenReturn("lama");

        Date date = new Date();

        when(dataService.getCreatedDate(auditedObjectFormList)).thenReturn(date);

        this.mockMvc.perform(get("/objectinfo/{id}", 1)
                .session(mockSession)
                .accept(MediaType.ALL)
                .param("type", "Account"))
                .andExpect(status().isOk())
                .andExpect(view().name("auditedmodel"))
                .andExpect(model().attribute("auditedList", auditedObjectFormList))
                .andExpect(model().attribute("createdBy", "lama"))
                .andExpect(model().attribute("createdDate", date));
    }
}
