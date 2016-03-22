package com.elstele.bill.test.datasrv;


import com.elstele.bill.dao.interfaces.UploadedFileInfoDAO;
import com.elstele.bill.datasrv.impl.UploadedFileInfoDataServiceImpl;
import com.elstele.bill.domain.UploadedFileInfo;
import com.elstele.bill.form.UploadedFileInfoForm;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.FileStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UploadedFileInfoDataServiceTest {
    @Mock
    UploadedFileInfoDAO dao;

    @InjectMocks
    UploadedFileInfoDataServiceImpl dataService;

    private UploadedFileInfo fileInfo;
    private UploadedFileInfoForm fileForm;

    @Before
    public void  setUp(){
        fileInfo = new UploadedFileInfo();
        fileInfo.setId(1);
        fileInfo.setFileStatus(FileStatus.NEW);
        fileInfo.setFileName("file1");

        fileForm = new UploadedFileInfoForm();
        fileForm.setId(1);
        fileForm.setFileStatus(FileStatus.NEW);
        fileForm.setFileName("file1");
    }

    @After
    public void tearDown(){
        dataService = null;
        fileInfo = null;
        fileForm = null;
    }

    @Test
    public void getUploadedFileInfoListTest(){
        List<UploadedFileInfo> fileInfos = new ArrayList<>();
        fileInfos.add(fileInfo);
        when(dao.getUploadedFileInfoList(Constants.KDF_FILE_TYPE)).thenReturn(fileInfos);

        List<UploadedFileInfoForm> actual = dataService.getUploadedFileInfoList(Constants.KDF_FILE_TYPE);
        assertTrue(actual.contains(fileForm));
    }

    @Test
    public void addUploadedFileInfo(){
        when(dao.create(fileInfo)).thenReturn(0);
        int i = dataService.addUploadedFileInfo(fileForm);
        assertTrue( i == 0 );
    }

    @Test
    public void getByIdTest(){
        when(dao.getById(1)).thenReturn(fileInfo);
        UploadedFileInfoForm actualForm = dataService.getById(1);
        assertEquals(actualForm.getId(), fileInfo.getId());
    }
}
