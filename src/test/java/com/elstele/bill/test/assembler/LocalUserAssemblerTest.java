package com.elstele.bill.test.assembler;

import com.elstele.bill.assembler.LocalUserAssembler;
import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.form.LocalUserForm;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
public class LocalUserAssemblerTest {
    private LocalUser localUser;
    private LocalUserForm localUserForm;
    private LocalUserAssembler assembler;

    @Before
    public void setUp() {
        assembler = new LocalUserAssembler();

        localUser = new LocalUser();
        localUser.setId(1);
        localUser.setUsername("alex");
        localUser.setPassword("1111");

        localUserForm = new LocalUserForm();
        localUserForm.setId(1);
        localUserForm.setUsername("alex");
        localUserForm.setPassword("1111");
    }

    @After
    public void tearDown() {
        assembler = null;
        localUser = null;
        localUserForm = null;
    }

    @Test
    public void fromBeanToFormTest(){
        LocalUserForm actual = assembler.fromBeanToForm(localUser);
        assertTrue(actual.equals(localUserForm));
    }

    @Test
    public void fromFormToBeanTest(){
        LocalUser actual = assembler.fromFormToBean(localUserForm);
        assertTrue(actual.equals(localUser));
    }
}
