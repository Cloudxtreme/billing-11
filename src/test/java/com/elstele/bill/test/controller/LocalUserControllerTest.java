package com.elstele.bill.test.controller;

import com.elstele.bill.controller.LocalUserController;
import com.elstele.bill.datasrv.interfaces.ActivityDataService;
import com.elstele.bill.form.ActivityForm;
import com.elstele.bill.test.builder.form.ActivityFormBuilder;
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
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:test-servlet-context.xml")
public class LocalUserControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private MockHttpSession mockSession;

    @InjectMocks
    LocalUserController controller;

    @Mock
    ActivityDataService activityDataService;

    private List<ActivityForm> activityFormList;
    private ActivityForm form;
    private ActivityFormBuilder builder;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());

        activityFormList = new ArrayList<>();
        builder = new ActivityFormBuilder();
        form = builder.build().withId(1).withName("test").withDescription("des").getRes();
    }

    @Test
    public void activityDeleteTest() throws Exception {
        when(activityDataService.listActivity()).thenReturn(activityFormList);
        MvcResult result = this.mockMvc.perform(get("/activity/{id}/delete", 1)
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("activity_list"))
                .andReturn();
        List<ActivityForm> actualList = (List<ActivityForm>) result.getModelAndView().getModel().get("activityList");
        assertEquals(actualList, activityFormList);
    }

    @Test
    public void activityUpdateTest() throws Exception {
        when(activityDataService.getActivityFormById(1)).thenReturn(form);
        MvcResult result = this.mockMvc.perform(get("/activity/{id}/update", 1)
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("activity_form"))
                .andReturn();
        ActivityForm actual = (ActivityForm) result.getModelAndView().getModel().get("activityForm");
        assertEquals(actual, form);
    }

    @Test
    public void activityAddTest() throws Exception {
        this.mockMvc.perform(get("/addactivity")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("activity_form"))
                .andReturn();
    }


}
