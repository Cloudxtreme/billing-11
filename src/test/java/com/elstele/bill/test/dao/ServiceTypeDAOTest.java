package com.elstele.bill.test.dao;

import com.elstele.bill.dao.ServiceTypeDAO;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.domain.Transaction;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Status;
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
        ServiceType serviceType1 = new ServiceType();
        serviceType1.setPrice(70F);
        serviceType1.setName("Serv1");
        serviceType1.setDescription("Description1");
        serviceType1.setStatus(Status.ACTIVE);

        ServiceType serviceType2 = new ServiceType();
        serviceType2.setPrice(150F);
        serviceType2.setName("Serv2");
        serviceType2.setDescription("Description2");
        serviceType2.setStatus(Status.ACTIVE);

        ServiceType serviceType3 = new ServiceType();
        serviceType3.setPrice(40F);
        serviceType3.setName("Serv3");
        serviceType3.setDescription("Description3");
        serviceType3.setStatus(Status.ACTIVE);

        int serviceID1 = serviceTypeDAO.create(serviceType1);
        int serviceID2 = serviceTypeDAO.create(serviceType2);
        int serviceID3 = serviceTypeDAO.create(serviceType3);

        ServiceType bean1 = serviceTypeDAO.getById(serviceID1);
        ServiceType bean2 = serviceTypeDAO.getById(serviceID2);
        ServiceType bean0 = serviceTypeDAO.getById(0);

        assertTrue(bean0 == null);
        assertTrue(serviceType1.equals(bean1));
        assertTrue(serviceType2.equals(bean2));


        /*--- Get List ---*/
        List<ServiceType> serviceTypeList = new ArrayList<ServiceType>();
        serviceTypeList.add(serviceType1);
        serviceTypeList.add(serviceType2);
        serviceTypeList.add(serviceType3);
        List<ServiceType> serviceTypeListFromDB = serviceTypeDAO.listServiceType();
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