package com.elstele.bill.test.dao;


import com.elstele.bill.dao.interfaces.UploadedFileInfoDAO;
import com.elstele.bill.domain.UploadedFileInfo;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
@TransactionConfiguration
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UploadedFileInfoDAOTest {

    @Autowired
    UploadedFileInfoDAO uploadedFileInfoDAO;

    private List<UploadedFileInfo> expected;

    @Before
    public void setUp(){
        expected = new ArrayList<>();

        UploadedFileInfo info1 = new UploadedFileInfo();
        info1.setStatus(Status.ACTIVE);
        UploadedFileInfo info2 = new UploadedFileInfo();
        info2.setStatus(Status.DELETED);
        UploadedFileInfo info3 = new UploadedFileInfo();
        info3.setStatus(Status.ACTIVE);

        uploadedFileInfoDAO.save(info1);
        uploadedFileInfoDAO.save(info2);
        uploadedFileInfoDAO.save(info3);

        expected.add(info1);
        expected.add(info2);
        expected.add(info3);
    }

    @After
    public void tearDown(){
        expected = null;
    }

    @Test
    public void getUploadedFileInfoListTest(){
        List<UploadedFileInfo> actual = uploadedFileInfoDAO.getUploadedFileInfoList(Constants.KDF_FILE_TYPE);
        assertEquals(actual, expected);
    }
}
