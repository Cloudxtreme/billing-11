package com.elstele.bill.test.assembler;


import com.elstele.bill.Builders.bean.AuditedObjectBuilder;
import com.elstele.bill.Builders.bean.ExternalPaymentBuilder;
import com.elstele.bill.Builders.form.AuditedObjectFormBuilder;
import com.elstele.bill.Builders.form.ExternalPaymentFormBuilder;
import com.elstele.bill.assembler.AuditedObjectAssembler;
import com.elstele.bill.assembler.ExternalPaymentAssembler;
import com.elstele.bill.domain.AuditedObject;
import com.elstele.bill.domain.ExternalPaymentTransaction;
import com.elstele.bill.form.AuditedObjectForm;
import com.elstele.bill.form.ExternalPaymentForm;
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
public class ExternalPaymentAssemblerTest {
    private ExternalPaymentTransaction bean;
    private ExternalPaymentForm form;
    private ExternalPaymentAssembler assembler;

    @Before
    public void setUp(){
        ExternalPaymentBuilder builder = new ExternalPaymentBuilder();
        bean = builder.build()
                .withPayAccount("test")
                .withId(1)
                .withPayAmount(11f)
                .withCheck(true)
                .getRes();

        ExternalPaymentFormBuilder formBuilder = new ExternalPaymentFormBuilder();
        form = formBuilder.build()
                .withPayAccount("test")
                .withId(1)
                .withPayAmount(11f)
                .withCheck(true)
                .getRes();

        assembler = new ExternalPaymentAssembler();
    }

    @After
    public void tearDown(){
        bean= null;
        form = null;
        assembler = null;
    }

    @Test
    public void fromBeanToFormTest(){
        ExternalPaymentForm actual = assembler.fromBeanToForm(bean);
        assertTrue(actual.equals(form));
    }

    @Test
    public void fromFormToBeanTest(){
        ExternalPaymentTransaction actual = assembler.fromFormToBean(form);
        assertTrue(actual.equals(bean));
    }


}
