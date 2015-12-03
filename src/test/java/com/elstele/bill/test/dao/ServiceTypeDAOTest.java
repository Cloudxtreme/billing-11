package com.elstele.bill.test.dao;

import com.elstele.bill.dao.interfaces.ServiceTypeDAO;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.domain.Transaction;
import com.elstele.bill.test.builder.ObjectBuilder;
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

import java.util.ArrayList;
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
    private ServiceTypeDAO serviceTypeDAO;

    @Before
    public void setUp() {
        String hql = String.format("delete from ServiceType");
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.executeUpdate();
    }

    @Test
    public void dbTransactionManupulation() {
        ObjectBuilder objectBuilder = new ObjectBuilder();
        ServiceType serviceType1 = objectBuilder.createServiceType(1,70F,"Serv1","Description1");
        ServiceType serviceType2 = objectBuilder.createServiceType(2,150F,"Serv2","Description2");
        ServiceType serviceType3 = objectBuilder.createServiceType(3,40F,"Serv3","Description3");
        List<ServiceType> serviceTypeList = new ArrayList<ServiceType>();
        serviceTypeList.add(serviceType1);
        serviceTypeList.add(serviceType2);
        serviceTypeList.add(serviceType3);

        int serviceID1 = serviceTypeDAO.create(serviceType1);
        int serviceID2 = serviceTypeDAO.create(serviceType2);
        int serviceID3 = serviceTypeDAO.create(serviceType3);

        ServiceType bean1 = serviceTypeDAO.getById(serviceID1);
        ServiceType bean2 = serviceTypeDAO.getById(serviceID2);
        ServiceType bean0 = serviceTypeDAO.getById(0);
        List<ServiceType> serviceTypeListFromDB = serviceTypeDAO.listServiceType();

        assertTrue(bean0 == null);
        assertTrue(serviceType1.equals(bean1));
        assertTrue(serviceType2.equals(bean2));
        assertTrue(serviceTypeList.equals(serviceTypeListFromDB));

        /*--- Mark Object As Deleted and GetList ---*/
        serviceTypeDAO.setStatusDelete(serviceID3);
        ServiceType markAsDeletedServType = serviceTypeDAO.getById(serviceID3);
        assertTrue(markAsDeletedServType.getStatus().equals(Status.DELETED));
        List<ServiceType> serviceTypeListFromDB2 = serviceTypeDAO.listServiceType();
        assertFalse(serviceTypeList.equals(serviceTypeListFromDB2));

        /*--- Delete ---*/
        assertFalse(markAsDeletedServType == null);
        serviceTypeDAO.delete(serviceID3);
        markAsDeletedServType = serviceTypeDAO.getById(serviceID3);
        assertTrue(markAsDeletedServType == null);

        /*--- Update ---*/
        serviceType1.setDescription("NewOneDescription");
        serviceTypeDAO.update(serviceType1);
        bean1 = serviceTypeDAO.getById(serviceID1);
        assertTrue(bean1.equals(serviceType1));

    }
}