package com.elstele.bill.test.assembler;
import com.elstele.bill.assembler.ServiceTypeAssembler;
import com.elstele.bill.domain.ServiceInternetAttribute;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.form.ServiceInternetAttributeForm;
import com.elstele.bill.form.ServiceTypeForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
public class ServiceTypeAssemblerTest {
    private ServiceTypeAssembler assembler;
    private ServiceType serviceType;
    private ServiceTypeForm serviceTypeForm;
    private ServiceInternetAttribute serviceInternetAttribute;
    private ServiceInternetAttributeForm serviceInternetAttributeForm;

    @Before
    public void setUp(){
        assembler = new ServiceTypeAssembler();

        serviceType = new ServiceType();
        serviceType.setId(1);
        serviceType.setName("service");

        serviceTypeForm = new ServiceTypeForm();
        serviceTypeForm.setId(1);
        serviceTypeForm.setName("service");

        serviceInternetAttribute = new ServiceInternetAttribute();
        serviceInternetAttribute.setId(2);
        serviceInternetAttribute.setValue("111");
        serviceInternetAttribute.setServiceType(new ServiceType());

        serviceInternetAttributeForm = new ServiceInternetAttributeForm();
        serviceInternetAttributeForm.setId(2);
        serviceInternetAttributeForm.setValue("111");
    }

    @Test
    public void fromBeanToFormTest(){
        ServiceTypeForm actual = assembler.fromBeanToForm(serviceType);
        assertTrue(actual.equals(serviceTypeForm));
    }

    @Test
    public void fromFormToBeanTest(){
        ServiceType actual = assembler.fromFormToBean(serviceTypeForm);
        assertTrue(actual.equals(serviceType));
    }

    @Test
    public void fromBeanToFormIpSubnetTest(){
        ServiceInternetAttributeForm actual = assembler.fromServiceInternetAttributeBeanToForm(serviceInternetAttribute);
        assertTrue(actual.equals(serviceInternetAttributeForm));
    }

    @Test
    public void fromFormToBeanIpSubnetTest(){
        ServiceInternetAttribute actual = assembler.fromServiceInternetAttributeFormToBean(serviceInternetAttributeForm);
        assertTrue(actual.equals(serviceInternetAttribute));
    }
}
