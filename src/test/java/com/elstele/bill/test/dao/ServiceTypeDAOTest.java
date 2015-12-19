package com.elstele.bill.test.dao;

import com.elstele.bill.dao.impl.ServiceTypeDAOImpl;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.test.builder.bean.ServiceTypeBuilder;
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

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/test-servlet-context.xml")
@TransactionConfiguration
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceTypeDAOTest {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private ServiceTypeDAOImpl serviceTypeDAO;

    private ServiceType serviceType1;
    private ServiceType serviceType2;
    private ServiceType serviceType3;

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
        serviceType1 = stb.build().withName("stb1").withServiceType(Constants.SERVICE_INTERNET).withRandomAttribute().withDescription("desrc1").withPrice(44F).withBussType(Constants.AccountType.PRIVATE).getRes();
        serviceType2 = stb.build().withName("stb2").withServiceType(Constants.SERVICE_PHONE).withPrice(90F).withRandomAttribute().withBussType(Constants.AccountType.PRIVATE).getRes();
        serviceType3 = stb.build().withName("stb3").withServiceType(Constants.SERVICE_MARKER).withPrice(111F).withDescription("descr3").withBussType(Constants.AccountType.LEGAL).getRes();
    }

    @Test
    public void a_createServiceType(){
        int serviceId1 = serviceTypeDAO.create(serviceType1);
        int serviceId2 = serviceTypeDAO.create(serviceType2);
        int serviceId3 = serviceTypeDAO.create(serviceType3);

        ServiceType bean1 = serviceTypeDAO.getById(serviceId1);
        ServiceType bean2 = serviceTypeDAO.getById(serviceId2);
        ServiceType bean0 = serviceTypeDAO.getById(0);

        assertTrue(bean0 == null);
        assertTrue(serviceType1.equals(bean1));
        assertTrue(serviceType2.equals(bean2));

    }
    @Test
    public void b_updateServiceType(){
        int serviceId1 = serviceTypeDAO.create(serviceType1);

        serviceType1.setDescription("NewOneDescription");
        serviceTypeDAO.update(serviceType1);
        ServiceType bean = serviceTypeDAO.getById(serviceId1);
        assertTrue(bean.equals(serviceType1));

    }
    @Test
    public void c_deleteServiceType(){
        int serviceId = serviceTypeDAO.create(serviceType3);

        serviceTypeDAO.setStatusDelete(serviceId);
        ServiceType markAsDeletedServType = serviceTypeDAO.getById(serviceId);
        assertTrue(markAsDeletedServType.getStatus().equals(Status.DELETED));

        assertFalse(markAsDeletedServType == null);
        serviceTypeDAO.delete(serviceId);
        assertTrue(serviceTypeDAO.getById(serviceId) == null);
    }

    @Test
    public void d_serviceTypeList() {
        int serviceId1 = serviceTypeDAO.create(serviceType1);
        int serviceId2 = serviceTypeDAO.create(serviceType2);
        int serviceId3 = serviceTypeDAO.create(serviceType3);

        List<ServiceType> serviceTypeList = serviceTypeDAO.listServiceType();

        assertTrue(serviceTypeList.size() == 3);
        assertTrue(serviceTypeList.contains(serviceType1));
        assertTrue(serviceTypeList.contains(serviceType2));
        assertTrue(serviceTypeList.contains(serviceType3));

        serviceTypeDAO.setStatusDelete(serviceId1);
        List<ServiceType> serviceTypeList2 = serviceTypeDAO.listServiceType();
        assertTrue(serviceTypeList2.size() == 2);
    }

    @Test
    public void e_serviceTypeListByGivenType() {
        int serviceId1 = serviceTypeDAO.create(serviceType1);
        int serviceId2 = serviceTypeDAO.create(serviceType2);
        int serviceId3 = serviceTypeDAO.create(serviceType3);

        List<ServiceType> serviceTypeList = serviceTypeDAO.listServiceType(Constants.SERVICE_PHONE);

        assertTrue(serviceTypeList.size() == 1);
        assertTrue(serviceTypeList.contains(serviceType2));
        assertFalse(serviceTypeList.contains(serviceType1));
        assertFalse(serviceTypeList.contains(serviceType3));
    }

    @Test
    public void f_serviceTypeListByBussType() {
        int serviceId1 = serviceTypeDAO.create(serviceType1);
        int serviceId2 = serviceTypeDAO.create(serviceType2);
        int serviceId3 = serviceTypeDAO.create(serviceType3);

        List<ServiceType> serviceTypeList = serviceTypeDAO.listServiceTypeByBussType(Constants.AccountType.PRIVATE);

        assertTrue(serviceTypeList.size() == 2);
        assertTrue(serviceTypeList.contains(serviceType1));
        assertTrue(serviceTypeList.contains(serviceType2));
        assertFalse(serviceTypeList.contains(serviceType3));
   }
}