package com.elstele.bill.test.assembler;

import com.elstele.bill.assembler.UserRoleAssembler;
import com.elstele.bill.domain.UserRole;
import com.elstele.bill.form.UserRoleForm;
import com.elstele.bill.test.builder.bean.UserRoleBuilder;
import com.elstele.bill.test.builder.form.UserRoleFormBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
public class UserRoleAssemblerTest {
    private UserRoleAssembler assembler;
    private UserRole bean;
    private UserRoleForm form;

    @Before
    public void setUp(){
        assembler = new UserRoleAssembler();

        UserRoleBuilder urb = new UserRoleBuilder();
        bean = urb.build().withId(33).withName("Name").withDescription("Descr").getRes();

        UserRoleFormBuilder urfb = new UserRoleFormBuilder();
        form = urfb.build().withId(33).withName("Name").withDescription("Descr").getRes();
    }

    @Test
    public void a_fromFormToBean(){
        UserRole beanGet = assembler.fromFormToBean(form);
        assertTrue(beanGet.equals(bean));
    }

    @Test
    public void b_fromBeanToForm(){
        UserRoleForm formGet = assembler.fromBeanToForm(bean);
        assertTrue(formGet.equals(form));
    }
}
