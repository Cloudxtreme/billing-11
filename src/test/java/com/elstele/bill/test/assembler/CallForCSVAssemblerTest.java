package com.elstele.bill.test.assembler;

import com.elstele.bill.assembler.CallForCSVAssembler;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.form.CallForCSVForm;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
public class CallForCSVAssemblerTest {
    private CallForCSV callForCSV;
    private CallForCSVForm callForCSVForm;
    private CallForCSVAssembler assembler;

    @Before
    public void setUp() {
        assembler = new CallForCSVAssembler();

        callForCSV = new CallForCSV();
        callForCSV.setNumberB("7891122");
        callForCSV.setCostCallTotal("1111");

        callForCSVForm = new CallForCSVForm();
        callForCSVForm.setNumberB("7891122");
        callForCSVForm.setCostCallTotal("1111");

    }

    @After
    public void tearDown() {
        assembler = null;
        callForCSV = null;
        callForCSVForm = null;
    }

    @Test
    public void fromBeanToFormTest(){
        CallForCSVForm actual = assembler.fromBeanToForm(callForCSV);
        assertEquals(actual.getNumberB(), callForCSV.getNumberB());
        assertEquals(actual.getCostCallTotal(), callForCSV.getCostCallTotal());
    }

    @Test
    public void fromFormToBeanTest(){
        CallForCSV actual = assembler.fromFormToBean(callForCSVForm);
        assertEquals(actual.getNumberB(), callForCSVForm.getNumberB());
        assertEquals(actual.getCostCallTotal(), callForCSVForm.getCostCallTotal());
    }

}
