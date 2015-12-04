package com.elstele.bill.test.assembler;


import com.elstele.bill.assembler.UploadedFileInfoAssembler;
import com.elstele.bill.domain.UploadedFileInfo;
import com.elstele.bill.form.UploadedFileInfoForm;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
public class UploadedFileInfoAssemblerTest {

    private UploadedFileInfo fileInfo;
    private UploadedFileInfoForm form;
    private UploadedFileInfoAssembler assembler;

    @Before
    public void setUp() {
        assembler = new UploadedFileInfoAssembler();

        fileInfo = new UploadedFileInfo();
        fileInfo.setId(1);
        fileInfo.setFileSize(1000l);

        form = new UploadedFileInfoForm();
        form.setId(1);
        form.setFileSize(1000l);

    }

    @After
    public void tearDown() {
        assembler = null;
        fileInfo = null;
        form = null;
    }

    @Test
    public void fromBeanToFormTest(){
        UploadedFileInfoForm actual = assembler.fromBeanToForm(fileInfo);
        assertTrue(actual.equals(form));
    }

    @Test
    public void fromFormToBeanTest(){
        UploadedFileInfo actual = assembler.fromFormToBean(form);
        assertTrue(actual.equals(fileInfo));
    }
}
