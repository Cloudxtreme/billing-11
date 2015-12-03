package com.elstele.bill.test.assembler;

import com.elstele.bill.assembler.DeviceTypesAssembler;
import com.elstele.bill.domain.DeviceTypes;
import com.elstele.bill.form.DeviceTypesForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
public class DeviceTypesAssemblerTest {
    private DeviceTypesAssembler assembler;
    private DeviceTypes types;
    private DeviceTypesForm form;

    @Before
    public void setUp(){
        assembler = new DeviceTypesAssembler();

        types= new DeviceTypes();
        types.setPortsNumber(22);
        types.setId(1);

        form  = new DeviceTypesForm();
        form.setId(1);
        form.setPortsNumber(22);
    }

    @Test
    public void fromBeanToFormTest(){
        DeviceTypesForm actual = assembler.fromBeanToForm(types);
        assertEquals(actual.getId(), types.getId());
        assertEquals(actual.getPortsNumber(), types.getPortsNumber());
    }

    @Test
    public void fromFormToBeanTest(){
        DeviceTypes actual = assembler.fromFormToBean(form);
        assertEquals(actual.getId(), form.getId());
        assertEquals(actual.getPortsNumber(), form.getPortsNumber());
    }
}
