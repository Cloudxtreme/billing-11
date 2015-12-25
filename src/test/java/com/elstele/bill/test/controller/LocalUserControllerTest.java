package com.elstele.bill.test.controller;

import com.elstele.bill.controller.LocalUserController;
import com.elstele.bill.datasrv.interfaces.ActivityDataService;
import com.elstele.bill.datasrv.interfaces.LocalUserDataService;
import com.elstele.bill.datasrv.interfaces.UserRoleDataService;
import com.elstele.bill.form.ActivityForm;
import com.elstele.bill.form.LocalUserForm;
import com.elstele.bill.form.UserRoleForm;
import com.elstele.bill.test.builder.form.ActivityFormBuilder;
import com.elstele.bill.test.builder.form.LocalUserFormBuilder;
import com.elstele.bill.test.builder.form.UserRoleFormBuilder;
import com.elstele.bill.validator.LocalUserValidator;
import com.elstele.bill.validator.UserRoleValidator;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

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
    @Mock
    UserRoleDataService userRoleDataService;
    @Mock
    LocalUserDataService localUserDataService;
    @Mock
    UserRoleValidator userRoleValidator;
    ;
    @Mock
    LocalUserValidator localUserValidator;

    private List<ActivityForm> activityFormList;
    private ActivityForm form;
    private ActivityFormBuilder builder;

    private UserRoleFormBuilder userRoleFormBuilder;
    private UserRoleForm userRoleForm;
    private List<UserRoleForm> userRoleFormList;

    private LocalUserFormBuilder localUserFormBuilder;
    private List<LocalUserForm> localUserFormList;
    private LocalUserForm localUserForm;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());

        activityFormList = new ArrayList<>();
        builder = new ActivityFormBuilder();
        form = builder.build().withId(1).withName("test").withDescription("des").getRes();

        userRoleFormBuilder = new UserRoleFormBuilder();
        userRoleForm = userRoleFormBuilder.build().withId(1).withName("test").getRes();
        userRoleFormList = new ArrayList<>();
        userRoleFormList.add(userRoleForm);

        localUserFormBuilder = new LocalUserFormBuilder();
        localUserFormList = new ArrayList<>();
        localUserForm = localUserFormBuilder.build().withId(1).withUserName("test").withPassword("pass").withPasswordConfirm("pass").getRes();
        localUserFormList.add(localUserForm);
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
                .andExpect(view().name("activity_form"));
    }

    @Test
    public void activityListTest() throws Exception {
        when(activityDataService.listActivity()).thenReturn(activityFormList);
        MvcResult result = this.mockMvc.perform(get("/activitylist")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("activity_list"))
                .andReturn();
        List<ActivityForm> actual = (List<ActivityForm>) result.getModelAndView().getModel().get("activityList");
        assertEquals(actual, activityFormList);
    }

    @Test
    public void userRoleDeleteTest() throws Exception {
        when(activityDataService.listActivity()).thenReturn(activityFormList);
        when(userRoleDataService.listUserRole()).thenReturn(userRoleFormList);

        MvcResult result = this.mockMvc.perform(get("/user_role/{id}/delete", 1)
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("user_role_list"))
                .andReturn();
        List<ActivityForm> actual = (List<ActivityForm>) result.getModelAndView().getModel().get("activityList");
        assertEquals(actual, activityFormList);
        List<UserRoleForm> actualUser = (List<UserRoleForm>) result.getModelAndView().getModel().get("userRoleList");
        assertEquals(actualUser, userRoleFormList);
    }

    @Test
    public void userRoleUpdateTest() throws Exception {
        when(userRoleDataService.getUserRoleFormById(1)).thenReturn(userRoleForm);
        when(activityDataService.listActivity()).thenReturn(activityFormList);

        MvcResult result = this.mockMvc.perform(get("/user_role/{id}/update", 1)
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("userRoleFormModel"))
                .andReturn();

        List<ActivityForm> actual = (List<ActivityForm>) result.getModelAndView().getModel().get("activityList");
        assertEquals(actual, activityFormList);
    }

    @Test
    public void userRoleAddTest() throws Exception {
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);

        this.mockMvc.perform(post("/user_role_form")
                .session(mockSession)
                .requestAttr("userRoleForm", userRoleForm)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("user_role_list"));
    }

    @Test
    public void userRoleListTest() throws Exception {
        when(activityDataService.listActivity()).thenReturn(activityFormList);
        MvcResult result = this.mockMvc.perform(get("/userrolelist")
                .session(mockSession)
                .requestAttr("userRoleForm", userRoleForm)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("user_role_list"))
                .andReturn();
        List<ActivityForm> actual = (List<ActivityForm>) result.getModelAndView().getModel().get("activityList");
        assertEquals(actual, activityFormList);
    }

    @Test
    public void userDeleteTest() throws Exception {
        when(userRoleDataService.listUserRole()).thenReturn(userRoleFormList);

        MvcResult result = this.mockMvc.perform(get("/user/{id}/delete", 1)
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("user_panel"))
                .andReturn();
        String message = result.getModelAndView().getModel().get("successMessage").toString();
        assertTrue(message.equals("User was successfully deleted."));
    }

    @Test
    public void userUpdateTest() throws Exception {
        when(userRoleDataService.listUserRole()).thenReturn(userRoleFormList);

        MvcResult result = this.mockMvc.perform(get("/user/{id}/update", 1)
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("user_form"))
                .andReturn();
        List<UserRoleForm> actualUser = (List<UserRoleForm>) result.getModelAndView().getModel().get("roleList");
        assertEquals(actualUser, userRoleFormList);
    }

    @Test
    public void userAddTest() throws Exception {
        when(userRoleDataService.listUserRole()).thenReturn(userRoleFormList);
        this.mockMvc.perform(get("/userform")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("user_form"));
    }

    @Test
    public void userAddPostTest() throws Exception {
        this.mockMvc.perform(post("/userform")
                .session(mockSession)
                .sessionAttr("localUserForm", localUserForm)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("user_panel"));
    }

    @Test
    public void userListTest() throws Exception {
        this.mockMvc.perform(get("/userpanel")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("user_panel"));
    }

}
