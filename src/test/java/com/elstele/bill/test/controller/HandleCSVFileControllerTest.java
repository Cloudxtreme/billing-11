package com.elstele.bill.test.controller;


import com.elstele.bill.controller.HandleCSVFileController;
import com.elstele.bill.datasrv.interfaces.CSVParserDataService;
import com.elstele.bill.datasrv.interfaces.ReportDataService;
import com.elstele.bill.filesWorkers.FileDownloadWorker;
import com.elstele.bill.exceptions.IncorrectReportNameException;
import com.elstele.bill.filesWorkers.FileTreeGenerater;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/test/resources/test-servlet-context.xml")
public class HandleCSVFileControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private MockHttpSession mockSession;

    @InjectMocks
    HandleCSVFileController controller;

    @Mock
    ServletContext ctx;
    @Mock
    FileDownloadWorker fileDownloadWorker;
    @Mock
    ReportDataService reportDataService;
    @Mock
    CSVParserDataService csvParserDataService;
    @Mock
    FileTreeGenerater fileTreeGenerater;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockSession = new MockHttpSession(wac.getServletContext(), UUID.randomUUID().toString());
    }

    @Test
    public void uploadcsvfileTest() throws Exception {
        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.csv", "text/csv", "some xml".getBytes());

        this.mockMvc.perform(MockMvcRequestBuilders.fileUpload("/uploadcsvfile")
                .file(firstFile))
                .andExpect(status().is(200));
    }


}
