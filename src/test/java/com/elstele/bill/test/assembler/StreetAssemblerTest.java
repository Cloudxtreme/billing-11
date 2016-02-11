package com.elstele.bill.test.assembler;
import com.elstele.bill.Builders.bean.StreetBuilder;
import com.elstele.bill.Builders.form.StreetFormBuilder;
import com.elstele.bill.assembler.StreetAssembler;
import com.elstele.bill.domain.Street;
import com.elstele.bill.form.StreetForm;
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
public class StreetAssemblerTest {
    private Street bean;
    private StreetForm form;
    private StreetAssembler assembler;

    @Before
    public void setUp(){
        StreetBuilder builder = new StreetBuilder();
        bean = builder.build()
                .withName("test")
                .withId(1)
                .getRes();

        StreetFormBuilder formBuilder = new StreetFormBuilder();
        form = formBuilder.build()
                .withName("test")
                .withId(1)
                .getRes();

        assembler = new StreetAssembler();
    }

    @After
    public void tearDown(){
        bean= null;
        form = null;
        assembler = null;
    }

    @Test
    public void fromBeanToFormTest(){
        StreetForm actual = assembler.fromBeanToForm(bean);
        assertTrue(actual.equals(form));
    }

    @Test
    public void fromFormToBeanTest(){
        Street actual = assembler.fromFormToBean(form);
        assertTrue(actual.equals(bean));
    }
}
