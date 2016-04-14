package com.elstele.bill.test.controller;

import com.elstele.bill.controller.HandleKDFController;
import com.elstele.bill.datasrv.interfaces.UploadedFileInfoDataService;
import com.elstele.bill.form.UploadedFileInfoForm;
import com.elstele.bill.test.builder.form.UploadedFileInfoFormBuilder;
import com.elstele.bill.utils.Constants;
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

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:test-servlet-context.xml")
public class HandleKDFFileControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private MockHttpSession mockSession;

    @InjectMocks
    HandleKDFController controller;

    @Mock
    UploadedFileInfoDataService uploadedFileInfoDataService;

    private UploadedFileInfoFormBuilder builder;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());

        builder=new UploadedFileInfoFormBuilder();
    }

    @Test
    public void addLoadedFilesTest() throws Exception {
        List<UploadedFileInfoForm> list = new ArrayList<>();
        UploadedFileInfoForm form = builder.build().withId(1).withFileName("test").withFileSize(11111l).getRes();
        list.add(form);
        when(uploadedFileInfoDataService.getUploadedFileInfoList(Constants.KDF_FILE_TYPE)).thenReturn(list);
        MvcResult result = this.mockMvc.perform(get("/uploadedfiles")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("uploadedKDFFiles"))
                .andReturn();

       List<UploadedFileInfoForm> actualList = (List<UploadedFileInfoForm>)result.getModelAndView().getModel().get("uploadedList");
        assertTrue(list.equals(actualList));
    }

    @Test
    public void deleteDeviceTest() throws Exception {
        UploadedFileInfoForm form = builder.build().withId(1).withFileName("test").withFileSize(11111l).getRes();
        when(uploadedFileInfoDataService.getById(1)).thenReturn(form);
        this.mockMvc.perform(get("/uploadedfiles")
                .session(mockSession)
                .content("1")
                .accept(MediaType.ALL))
                .andExpect(status().isOk());
    }

    @Test
    public void costTotalCalculate() throws Exception {
        this.mockMvc.perform(get("/uploadedfiles/handle/getprogress")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().string("0.0"));
    }
}
