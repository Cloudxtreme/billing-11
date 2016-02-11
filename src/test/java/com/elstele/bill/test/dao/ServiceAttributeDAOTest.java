package com.elstele.bill.test.dao;

import com.elstele.bill.dao.impl.ServiceAttributeDAOImpl;
import com.elstele.bill.dao.impl.ServiceTypeDAOImpl;
import com.elstele.bill.domain.ServiceInternetAttribute;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.Builders.bean.ServiceAttributeBuilder;
import com.elstele.bill.Builders.bean.ServiceTypeBuilder;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.Status;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/test-servlet-context.xml")
@TransactionConfiguration
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceAttributeDAOTest {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private ServiceAttributeDAOImpl serviceAttributeDAO;
    @Autowired
    private ServiceTypeDAOImpl serviceTypeDAO;

    private ServiceInternetAttribute serviceAttr1;
    private ServiceInternetAttribute serviceAttr2;

    @Before
    public void setUp() {
        String hql = String.format("delete from Service");
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.executeUpdate();
        hql = String.format("delete from ServiceType");
        query = sessionFactory.getCurrentSession().createQuery(hql);
        query.executeUpdate();
        hql = String.format("delete from ServiceInternetAttribute");
        query = sessionFactory.getCurrentSession().createQuery(hql);
        query.executeUpdate();

        ServiceTypeBuilder stb = new ServiceTypeBuilder();
        ServiceType serviceType = stb.build().withName("stb1").withServiceType(Constants.SERVICE_INTERNET).withPrice(44F).getRes();
        Integer serviceTypeId = serviceTypeDAO.create(serviceType);

        ServiceAttributeBuilder sab = new ServiceAttributeBuilder();
        serviceAttr1 = sab.build().withAttribute("attribute1").withOperation("=").withValue("value1").withServiceType(serviceType).getRes();
        serviceAttr2 = sab.build().withAttribute("attribute2").withOperation("=>").withValue("value2").withServiceType(serviceType).getRes();
    }

    @Test
    public void a_createServiceAttribute(){
        Integer attributeId1 = serviceAttributeDAO.create(serviceAttr1);
        Integer attributeId2 = serviceAttributeDAO.create(serviceAttr2);

        ServiceInternetAttribute bean1 = serviceAttributeDAO.getById(attributeId1);
        ServiceInternetAttribute bean2 = serviceAttributeDAO.getById(attributeId2);
        ServiceInternetAttribute bean0 = serviceAttributeDAO.getById(0);

        assertTrue(bean0 == null);
        assertTrue(serviceAttr1.equals(bean1));
        assertTrue(serviceAttr2.equals(bean2));
    }

    @Test
    public void b_updateServiceAttribute(){
        Integer attributeId = serviceAttributeDAO.create(serviceAttr1);

        serviceAttr1.setValue("New Value For Test");
        serviceAttributeDAO.update(serviceAttr1);
        ServiceInternetAttribute bean = serviceAttributeDAO.getById(attributeId);
        assertTrue(bean.equals(serviceAttr1));
    }

    @Test
    public void c_deleteServiceAttribute(){
        Integer attributeId = serviceAttributeDAO.create(serviceAttr1);

        serviceAttributeDAO.setStatusDelete(attributeId);
        ServiceInternetAttribute markAsDeletedServAttr = serviceAttributeDAO.getById(attributeId);
        assertTrue(markAsDeletedServAttr.getStatus().equals(Status.DELETED));

        assertFalse(markAsDeletedServAttr == null);
        serviceAttributeDAO.delete(attributeId);
        assertTrue(serviceAttributeDAO.getById(attributeId) == null);
    }

    @Test
    public void d_serviceAttributeList() {
        Integer attributeId1 = serviceAttributeDAO.create(serviceAttr1);
        Integer attributeId2 = serviceAttributeDAO.create(serviceAttr2);

        List<ServiceInternetAttribute> serviceAttributeList = serviceAttributeDAO.getServiceInternetAttributesById(serviceAttr1.getServiceType().getId());

        assertTrue(serviceAttributeList.size() == 2);
        assertTrue(serviceAttributeList.contains(serviceAttr1));
        assertTrue(serviceAttributeList.contains(serviceAttr2));

        serviceAttributeDAO.setStatusDelete(serviceAttr1);
        List<ServiceInternetAttribute> serviceTypeList2 = serviceAttributeDAO.getServiceInternetAttributesById(serviceAttr1.getServiceType().getId());
        assertTrue(serviceTypeList2.size() == 1);
    }
}
