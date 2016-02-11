package com.elstele.bill.test.controller;

import com.elstele.bill.controller.StreetController;
import com.elstele.bill.datasrv.interfaces.StreetDataService;
import com.elstele.bill.domain.Street;
import com.elstele.bill.Builders.bean.StreetBuilder;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:test-servlet-context.xml")
public class StreetControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private MockHttpSession mockSession;

    @Mock
    StreetDataService streetDataService;

    @InjectMocks
    StreetController streetController;

    private List<Street> expectedList;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(streetController).build();
        mockSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());

        expectedList = new ArrayList<>();
        StreetBuilder streetBuilder = new StreetBuilder();
        Street street = streetBuilder.build().withId(1).withName("aaaaaa").getRes();
        Street street1 = streetBuilder.build().withId(2).withName("babab").getRes();
        Street street2 = streetBuilder.build().withId(1).withName("lalalala").getRes();
        expectedList.add(street);
        expectedList.add(street1);
        expectedList.add(street2);
    }

    @Test
    public void getListOfStreetsTest() throws Exception {
        when(streetDataService.getListOfStreets("A")).thenReturn(expectedList);
        MvcResult result = this.mockMvc.perform(post("/adddevice/getListOfStreets")
                .param("query", "A")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Street> list = mapper.readValue(content, mapper.getTypeFactory().constructCollectionType(
                ArrayList.class, Street.class));

        assertTrue(expectedList.equals(list));

    }
}
