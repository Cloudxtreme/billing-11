package com.elstele.bill.test.utils;

import com.elstele.bill.utils.LocalDirPathProvider;
import com.elstele.bill.utils.PropertiesHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletContext;

import static com.elstele.bill.utils.Constants.PATH_TO_CSV_FOLDER;
import static com.elstele.bill.utils.Constants.PATH_TO_DOCX_FOLDER;
import static com.elstele.bill.utils.Constants.PATH_TO_UPLOAD_FOLDER;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class LocalDirPathProviderTest {
    @Mock
    PropertiesHelper propertiesHelper;
    @Mock
    ServletContext ctx;

    @InjectMocks
    LocalDirPathProvider pathProvider;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getKDFDirectoryPathTestFirstCase(){
        when(propertiesHelper.getKDFFilesDirectory()).thenReturn("kdffiledir");
        String actual = pathProvider.getKDFDirectoryPath();
        assertTrue(actual.equals("kdffiledir"));
    }

    @Test
    public void getKDFDirectoryPathTestSecondCase() {
        when(propertiesHelper.getKDFFilesDirectory()).thenReturn(null);
        when(ctx.getRealPath(PATH_TO_UPLOAD_FOLDER)).thenReturn("kdffiledir");
        String actual = pathProvider.getKDFDirectoryPath();
        assertTrue(actual.equals("kdffiledir"));
    }

    @Test
    public void getCSVDirectoryPathTestFirstCase(){
        when(propertiesHelper.getCSVFilesDirectory()).thenReturn("csvFileDir");
        String actual = pathProvider.getCSVDirectoryPath();
        assertTrue(actual.equals("csvFileDir"));
    }

    @Test
    public void getCSVDirectoryPathTestSecondCase() {
        when(propertiesHelper.getCSVFilesDirectory()).thenReturn(null);
        when(ctx.getRealPath(PATH_TO_CSV_FOLDER)).thenReturn("csvFileDir");
        String actual = pathProvider.getCSVDirectoryPath();
        assertTrue(actual.equals("csvFileDir"));
    }

    @Test
    public void getDOCXDirectoryPathTestFirstCase(){
        when(propertiesHelper.getDOCXDirectory()).thenReturn("docxFileDir");
        String actual = pathProvider.getDOCXDirectoryPath();
        assertTrue(actual.equals("docxFileDir"));
    }

    @Test
    public void getDOCXDirectoryPathTestSecondCase() {
        when(propertiesHelper.getDOCXDirectory()).thenReturn(null);
        when(ctx.getRealPath(PATH_TO_DOCX_FOLDER)).thenReturn("docxFileDir");
        String actual = pathProvider.getDOCXDirectoryPath();
        assertTrue(actual.equals("docxFileDir"));
    }




}
