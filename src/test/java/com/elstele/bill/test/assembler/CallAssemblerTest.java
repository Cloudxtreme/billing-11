package com.elstele.bill.test.assembler;

import com.elstele.bill.assembler.CallAssembler;
import com.elstele.bill.domain.Call;
import com.elstele.bill.form.CallForm;
import com.elstele.bill.utils.Enums.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
public class CallAssemblerTest {
    private Call call;
    private CallForm callForm;
    private CallAssembler assembler;

    @Before
    public void setUp() {
        assembler = new CallAssembler();

        call = new Call();
        call.setId(1);
        call.setNumberB("7891122");
        call.setNumberA("8191911");
        call.setCostTotal(22.44f);
        call.setStatus(Status.BLOCKED);

        callForm = new CallForm();
        callForm.setId(1);
        callForm.setNumberB("7891122");
        callForm.setNumberA("8191911");
        callForm.setCostTotal(22.44f);
        callForm.setStatus(Status.ACTIVE);
    }

    @After
    public void tearDown() {
        assembler = null;
        call = null;
        callForm = null;
    }

    @Test
    public void fromBeanToFormTest(){
        CallForm actual = assembler.fromBeanToForm(call);
        assertFalse(actual.equals(callForm));
    }

    @Test
    public void fromFormToBeanTest(){
        Call actual = assembler.fromFormToBean(callForm);
        assertTrue(actual.equals(call));
    }
}


