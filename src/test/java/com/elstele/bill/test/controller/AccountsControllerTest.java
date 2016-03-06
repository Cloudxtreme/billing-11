package com.elstele.bill.test.controller;

import com.elstele.bill.controller.AccountsController;
import com.elstele.bill.datasrv.interfaces.AccountDataService;
import com.elstele.bill.datasrv.interfaces.TransactionDataService;
import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.form.TransactionForm;
import com.elstele.bill.test.builder.form.AccountFormBuilder;
import com.elstele.bill.test.builder.form.TransactionFormBuilder;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:test-servlet-context.xml")
public class AccountsControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private MockHttpSession mockSession;

    @InjectMocks
    AccountsController controller;

    @Mock
    AccountDataService accountDataService;
    @Mock
    TransactionDataService trService;
    @Mock
    Messagei18nHelper messagei18nHelper;

    private AccountFormBuilder builder;
    private List<AccountForm> list;
    private AccountForm form;
    private Set<AccountForm> set;
    private LocalUser user;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());

        builder = new AccountFormBuilder();

        list = new ArrayList<>();
        form = builder.build().withId(1).withAccName("test").withFIO("testFio").withAccType(Constants.AccountType.LEGAL).withBalance(99f).getRes();
        list.add(form);
        set= new HashSet<>();
        set.add(form);

        user = new LocalUser();
        user.setUsername("test");
    }

    @Test
    public void handleAccountHomeTest() throws Exception {
        List<Constants.AccountType> types = new ArrayList<Constants.AccountType>(Arrays.asList(Constants.AccountType.values()));
        MvcResult result = this.mockMvc.perform(get("/accounts/accountHome")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("accounts_list"))
                .andReturn();
        List<Constants.AccountType> actual = (List<Constants.AccountType>) result.getModelAndView().getModel().get("accountTypeList");
        assertEquals(actual, types);
    }

    @Test
    public void getAccountsListTest() throws Exception {
        when(accountDataService.getAccountsList(1, 1)).thenReturn(list);
        MvcResult result = this.mockMvc.perform(get("/accounts/accountsList")
                .param("rows", "1")
                .param("page", "1")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<AccountForm> actual = mapper.readValue(content, mapper.getTypeFactory().constructCollectionType(
                ArrayList.class, AccountForm.class));

        assertTrue(actual.equals(list));
    }

    @Test
    public void accountsShortList() throws Exception {
        when(accountDataService.getAccountsLiteFormList(1, 1)).thenReturn(list);
        MvcResult result = this.mockMvc.perform(get("/accounts/accountsShortList")
                .param("rows", "1")
                .param("page", "1")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<AccountForm> actual = mapper.readValue(content, mapper.getTypeFactory().constructCollectionType(
                ArrayList.class, AccountForm.class));

        assertTrue(actual.equals(list));
    }

    @Test
    public void getAccountTest() throws Exception {
        when(accountDataService.getAccountById(1)).thenReturn(form);
        MvcResult result = this.mockMvc.perform(get("/accounts/getAccount")
                .param("id", "1")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains(form.getFio()));
        assertTrue(content.contains(form.getId().toString()));
    }

    @Test
    public void addAccountFromFormTest() throws Exception {
        this.mockMvc.perform(post("/accounts/add")
                .requestAttr("accountForm", form)
                .session(mockSession)
                .sessionAttr(Constants.LOCAL_USER, user)
                .accept(MediaType.ALL))
                .andExpect(status().isOk());
    }

    @Test
    public void editAccountTest() throws Exception {
        this.mockMvc.perform(post("/accounts/editAccount")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new org.codehaus.jackson.map.ObjectMapper().writeValueAsString(form))
                .sessionAttr(Constants.LOCAL_USER, user)
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk());
    }

    @Test
    public void editAccountFullTest() throws Exception {
        TransactionFormBuilder trBuilder = new TransactionFormBuilder();
        TransactionForm trForm = trBuilder.build().withAccount(form).withComment("tes").withId(2).withPrice(22f).getRes();
        List<TransactionForm> expectedList = new ArrayList<>();
        expectedList.add(trForm);

        //TODO: get back call accountDataService.getAccountById()
        when(accountDataService.getAccountWithAllServicesById(1)).thenReturn(form);
        when(trService.getTransactionList(1, Constants.TRANSACTION_DISPLAY_LIMIT)).thenReturn(expectedList);
        MvcResult result = this.mockMvc.perform(get("/accounts/editFull/{id}", 1)
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("accountFull"))
                .andReturn();
        List<TransactionForm> actualTrList = (List<TransactionForm>)result.getModelAndView().getModel().get("transactionList");
        assertEquals(actualTrList, expectedList);
        AccountForm actualForm = (AccountForm)result.getModelAndView().getModel().get("accountForm");
        assertEquals(actualForm, form);
    }

    @Test
    public void deleteAccountTest() throws Exception {
        this.mockMvc.perform(get("/accounts/delete/{id}", 1)
                .session(mockSession)
                .sessionAttr(Constants.LOCAL_USER, user)
                .accept(MediaType.ALL))
                .andExpect(status().is(302));
    }

    @Test
    public void saveAccountFullTest() throws Exception {
        List<Constants.AccountType> types = new ArrayList<Constants.AccountType>(Arrays.asList(Constants.AccountType.values()));
        this.mockMvc.perform(post("/accounts/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new org.codehaus.jackson.map.ObjectMapper().writeValueAsString(form))
                .session(mockSession)
                .sessionAttr(Constants.LOCAL_USER, user)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("accounts_list"))
                .andExpect(model().attribute("accountTypeList", types));
    }

    @Test
    public void searchAccountTest() throws Exception {
        when(accountDataService.searchAccounts("test")).thenReturn(set);

        MvcResult result = this.mockMvc.perform(post("/accounts/accountsearch")
                .param("searchInput", "test")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("accountsearchModel"))
                .andReturn();
        Set<AccountForm> actualList = (Set<AccountForm>)result.getModelAndView().getModel().get("accountList");
        assertEquals(actualList, set);
    }
}
