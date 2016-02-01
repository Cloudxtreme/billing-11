package com.elstele.bill.test.controller;


import com.elstele.bill.controller.FileUploadController;
import com.elstele.bill.datasrv.interfaces.CallDataService;
import com.elstele.bill.datasrv.interfaces.ReportDataService;
import com.elstele.bill.datasrv.interfaces.UploadedFileInfoDataService;
import com.elstele.bill.filesWorkers.FileUploader;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

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
public class FileUploadControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private MockHttpSession mockSession;

    @Mock
    CallDataService callDataService;

    @Mock
    UploadedFileInfoDataService uploadedFileInfoDataService;

    @Mock
    ReportDataService reportDataService;

    @Mock
    FileUploader fileUploader;

    @InjectMocks
    FileUploadController controller;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());
    }

    @Test
    public void fileCSVFirstViewTest() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("2015");
        list.add("2014");
        when(callDataService.getYearsList()).thenReturn(list);

        MvcResult result = this.mockMvc.perform(get("/uploadcsvfile")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("uploadCSVFile"))
                .andReturn();
        ModelAndView content = result.getModelAndView();
        List<String> stringList = (List<String>)content.getModel().get("yearList");
        assertTrue(stringList.equals(list));
    }

    @Test
    public void setPageToUploadTest() throws Exception {
        this.mockMvc.perform(get("/uploadfile")
                .session(mockSession)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("uploadKDF"));
    }

    @Test
    public void putFileToFolderTest() throws Exception {
        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        MockMultipartFile secondFile = new MockMultipartFile("data", "other-file-name.data", "text/plain", "some other type".getBytes());

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.fileUpload("/uploadfile")
                .file(firstFile)
                .file(secondFile))
                .andExpect(status().is(200))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains(""));

    }
}
