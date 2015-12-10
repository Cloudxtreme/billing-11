package com.elstele.bill.test.dao;

import com.elstele.bill.dao.impl.AccountDAOImpl;
import com.elstele.bill.dao.impl.ServiceDAOImpl;
import com.elstele.bill.domain.*;
import com.elstele.bill.test.builder.bean.AccountBuilder;
import com.elstele.bill.test.builder.bean.ServiceBuilder;
import com.elstele.bill.test.builder.bean.ServiceInternetBuilder;
import com.elstele.bill.test.builder.bean.ServicePhoneBuilder;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.Status;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.junit.*;
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
public class ServiceDAOTest {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private ServiceDAOImpl serviceDAO;
    @Autowired
    private AccountDAOImpl accountDAO;

    private Service serviceMarker;
    private ServicePhone servicePhone;
    private ServiceInternet serviceInternet;

    @Before
    public void setUp() {
        String hql = String.format("delete from Service");
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.executeUpdate();
        hql = String.format("delete from Transaction");
        query = sessionFactory.getCurrentSession().createQuery(hql);
        query.executeUpdate();
        hql = String.format("delete from Account");
        query = sessionFactory.getCurrentSession().createQuery(hql);
        query.executeUpdate();
        hql = String.format("delete from ServiceType");
        query = sessionFactory.getCurrentSession().createQuery(hql);
        query.executeUpdate();

        AccountBuilder ab = new AccountBuilder();
        Account account = ab.build().withAccName("ACC_001").withAccType(Constants.AccountType.PRIVATE).withBalance(20f).withRandomPhyAddress().getRes();
        accountDAO.create(account);

        ServiceBuilder smb = new ServiceBuilder();
        serviceMarker = smb.build().randomService().withAccount(account).getRes();

        ServicePhoneBuilder spb = new ServicePhoneBuilder();
        servicePhone = spb.build().randomService().withAccount(account).getRes();

        ServiceInternetBuilder sib = new ServiceInternetBuilder();
        serviceInternet = sib.build().randomService().withAccount(account).getRes();
    }

    @After
    public void tearDown() {
        serviceDAO = null;
        accountDAO = null;
    }

    @Test
    public void a_createService(){
        Integer id1 = serviceDAO.create(serviceMarker);
        Integer id2 = serviceDAO.create(servicePhone);
        Integer id3 = serviceDAO.create(serviceInternet);

        Service bean1 = serviceDAO.getById(id1);
        Service bean2 = serviceDAO.getById(id2);
        Service bean3 = serviceDAO.getById(id3);
        Service bean0 = serviceDAO.getById(0);

        assertTrue(bean0 == null);
        assertTrue(serviceMarker.equals(bean1));
        assertTrue(servicePhone.equals(bean2));
        assertTrue(serviceInternet.equals(bean3));
    }

    @Test
    public void b_updateService(){
        Integer id = serviceDAO.create(serviceInternet);

        serviceInternet.setUsername("NewUser");
        serviceInternet.setUsername("NewPassword");
        serviceDAO.update(serviceInternet);
        Service bean = serviceDAO.getById(id);
        assertTrue(bean.equals(serviceInternet));
    }

    @Test
    public void c_deleteService(){
        Integer id = serviceDAO.create(servicePhone);

        serviceDAO.setStatusDelete(id);
        Service res = serviceDAO.getById(id);
        assertTrue(res.getStatus().equals(Status.DELETED));

        assertFalse(res == null);
        serviceDAO.delete(id);
        assertTrue(serviceDAO.getById(id) == null);
    }

    @Test
    public void d_serviceList() {
        int serviceId1 = serviceDAO.create(serviceMarker);
        int serviceId2 = serviceDAO.create(servicePhone);
        int serviceId3 = serviceDAO.create(serviceInternet);

        List<Service> serviceList = serviceDAO.listServices();

        assertTrue(serviceList.size() == 3);
        assertTrue(serviceList.contains(serviceMarker));
        assertTrue(serviceList.contains(servicePhone));
        assertTrue(serviceList.contains(serviceInternet));

        serviceDAO.setStatusDelete(serviceId1);
        List<Service> serviceList2 = serviceDAO.listServices();
        assertTrue(serviceList2.size() == 2);
    }
}
