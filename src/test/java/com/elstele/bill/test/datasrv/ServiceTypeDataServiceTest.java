package com.elstele.bill.test.datasrv;

import com.elstele.bill.dao.impl.ServiceAttributeDAOImpl;
import com.elstele.bill.dao.impl.ServiceTypeDAOImpl;
import com.elstele.bill.datasrv.impl.ServiceTypeDataServiceImpl;
import com.elstele.bill.domain.ServiceInternetAttribute;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.form.ServiceInternetAttributeForm;
import com.elstele.bill.form.ServiceTypeForm;
import com.elstele.bill.Builders.bean.ServiceAttributeBuilder;
import com.elstele.bill.Builders.bean.ServiceTypeBuilder;
import com.elstele.bill.Builders.form.ServiceAttributeFormBuilder;
import com.elstele.bill.Builders.form.ServiceTypeFormBuilder;
import com.elstele.bill.utils.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ServiceTypeDataServiceTest {

    @Mock
    private ServiceTypeDAOImpl serviceTypeDAO;
    @Mock
    private ServiceAttributeDAOImpl serviceAttributeDAO;
    @InjectMocks
    private ServiceTypeDataServiceImpl serviceTypeDataService;

    List<ServiceType> listSample = new ArrayList<>();
    List<ServiceTypeForm> listFormSample = new ArrayList<>();
    private ServiceType beanInternet;
    private ServiceType beanPhone;
    private ServiceType beanMarker;

    private ServiceTypeForm formInternet;

    @Before
    public void setUp() {
        beanSetUp();
        formSetUp();
    }

    @Test
    public void a_getServiceTypeFormById(){
        when(serviceTypeDAO.getById(1)).thenReturn(beanInternet);

        ServiceTypeForm servForm = serviceTypeDataService.getServiceTypeFormById(1);
        assertTrue(servForm.equals(formInternet));
    }

    @Test
    public void b_listServiceType(){
        when(serviceTypeDAO.listServiceType()).thenReturn(listSample);

        List<ServiceType> getList = serviceTypeDataService.listServiceType();
        assertTrue(getList.contains(beanInternet));
        assertTrue(getList.contains(beanMarker));
        assertTrue(getList.contains(beanPhone));
    }

    @Test
    public void c_listServiceTypeByGivenType(){
        listSample.remove(beanPhone);
        listSample.remove(beanMarker);
        when(serviceTypeDAO.listServiceType(Constants.SERVICE_INTERNET)).thenReturn(listSample);

        List<ServiceType> getList = serviceTypeDataService.listServiceType(Constants.SERVICE_INTERNET);
        assertTrue(getList.contains(beanInternet));
    }

    @Test
    public void d_listServiceTypeByBussType(){
        listSample.remove(beanInternet);
        when(serviceTypeDAO.listServiceTypeByBussType(Constants.AccountType.PRIVATE)).thenReturn(listSample);

        List<ServiceType> getList = serviceTypeDataService.listServiceTypeByBussType(Constants.AccountType.PRIVATE);
        assertTrue(getList.contains(beanMarker));
        assertTrue(getList.contains(beanPhone));
        assertFalse(getList.contains(beanInternet));
    }

    @Test
    public void e_listServiceAttribute(){
        ServiceAttributeBuilder sb = new ServiceAttributeBuilder();
        ServiceInternetAttribute attribute1 = sb.build().withId(4).withAttribute("Attribute 4").withOperation("=").withValue("Value 4").withServiceType(beanInternet).getRes();
        ServiceInternetAttribute attribute2 = sb.build().withId(7).withAttribute("Attribute 7").withOperation(">=").withValue("Value 7").withServiceType(beanInternet).getRes();
        List<ServiceInternetAttribute> attributeList = new ArrayList<>();
        attributeList.add(attribute1);
        attributeList.add(attribute2);

        when(serviceAttributeDAO.getServiceInternetAttributesById(1)).thenReturn(attributeList);

        ServiceAttributeFormBuilder sfb = new ServiceAttributeFormBuilder();
        ServiceInternetAttributeForm attributeForm1 = sfb.build().withId(4).withAttribute("Attribute 4").withOperation("=").withValue("Value 4").withServiceType(1).getRes();
        ServiceInternetAttributeForm attributeForm2 = sfb.build().withId(7).withAttribute("Attribute 7").withOperation(">=").withValue("Value 7").withServiceType(1).getRes();

        List<ServiceInternetAttributeForm> listAttributeForm = serviceTypeDataService.listServiceAttribute(1);
        assertTrue(listAttributeForm.contains(attributeForm1));
        assertTrue(listAttributeForm.contains(attributeForm2));
    }

    @Test
    public void f_getServiceAttributeForm(){
        ServiceAttributeBuilder sb = new ServiceAttributeBuilder();
        ServiceInternetAttribute attribute = sb.build().withId(4).withAttribute("Attribute 4").withOperation("=").withValue("Value 4").withServiceType(beanInternet).getRes();

        when(serviceAttributeDAO.getById(4)).thenReturn(attribute);

        ServiceAttributeFormBuilder sfb = new ServiceAttributeFormBuilder();
        ServiceInternetAttributeForm attributeForm = sfb.build().withId(4).withAttribute("Attribute 4").withOperation("=").withValue("Value 4").withServiceType(1).getRes();

        ServiceInternetAttributeForm getForm = serviceTypeDataService.getServiceAttributeForm(4,1);
        assertTrue(getForm.equals(attributeForm));
    }

    private void beanSetUp(){
        ServiceTypeBuilder stb = new ServiceTypeBuilder();
        beanInternet = stb.build().withId(1).withName("stb1").withServiceType(Constants.SERVICE_INTERNET).withDescription("desrc1").withPrice(44F).withBussType(Constants.AccountType.LEGAL).getRes();
        beanPhone = stb.build().withId(2).withName("stb2").withServiceType(Constants.SERVICE_PHONE).withPrice(90F).withBussType(Constants.AccountType.PRIVATE).getRes();
        beanMarker = stb.build().withId(3).withName("stb3").withServiceType(Constants.SERVICE_MARKER).withPrice(111F).withDescription("descr3").withBussType(Constants.AccountType.PRIVATE).getRes();

        listSample.add(beanInternet);
        listSample.add(beanPhone);
        listSample.add(beanMarker);
    }

    private void formSetUp(){
        ServiceTypeFormBuilder stfb = new ServiceTypeFormBuilder();
        formInternet = stfb.build().withId(1).withName("stb1").withServiceType(Constants.SERVICE_INTERNET).withDescription("desrc1").withPrice(44F).withBussType(Constants.AccountType.LEGAL).getRes();
        ServiceTypeForm formPhone = stfb.build().withId(2).withName("stb2").withServiceType(Constants.SERVICE_PHONE).withPrice(90F).withBussType(Constants.AccountType.PRIVATE).getRes();
        ServiceTypeForm formMarker = stfb.build().withId(3).withName("stb3").withServiceType(Constants.SERVICE_MARKER).withPrice(111F).withDescription("descr3").withBussType(Constants.AccountType.PRIVATE).getRes();

        listFormSample.add(formInternet);
        listFormSample.add(formPhone);
        listFormSample.add(formMarker);
    }
}
