package com.elstele.bill.test.controller;


import com.elstele.bill.controller.HandleCSVFileController;
import com.elstele.bill.datasrv.interfaces.CSVParserDataService;
import com.elstele.bill.datasrv.interfaces.ReportDataService;
import com.elstele.bill.filesWorkers.FileDownloadWorker;
import com.elstele.bill.utils.exceptions.IncorrectReportNameException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.ServletContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/test/resources/test-servlet-context.xml")
public class HandleCSVFileControllerTest {

    private MockMvc mockMvc;
    private String[] json;

    @InjectMocks
    HandleCSVFileController controllerUnderTest;

    @Mock
    ServletContext ctx;
    @Mock
    FileDownloadWorker fileDownloadWorker;
    @Mock
    ReportDataService reportDataService;
    @Mock
    CSVParserDataService csvParserDataService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();
    }

    @Test
    public void generateReportTestNegative() throws IncorrectReportNameException {
        json = new String[1];
        json[0 ] = "incorrect";
        reportDataService.createReport(json);
    }

}
