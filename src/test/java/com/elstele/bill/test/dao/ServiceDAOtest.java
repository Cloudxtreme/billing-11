package com.elstele.bill.test.dao;

import com.elstele.bill.dao.AccountDAO;
import com.elstele.bill.dao.ServiceDAO;
import com.elstele.bill.dao.ServiceTypeDAO;
import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Service;
import com.elstele.bill.domain.ServicePhone;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.test.builder.ObjectBuilder;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Status;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.elstele.bill.utils.Constants.*;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/test-servlet-context.xml")
@TransactionConfiguration
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceDAOtest {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private ServiceDAO serviceDAO;
    @Autowired
    private ServiceTypeDAO serviceTypeDAO;
    @Autowired
    private AccountDAO accountDAO;

    private Account account;
    private ServiceType serviceType;

    private Timestamp getTimestamp(){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }

    @Before
    public void setUp() {
        String hql = String.format("delete from Service");
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.executeUpdate();

        ObjectBuilder objectBuilder = new ObjectBuilder();
        account = objectBuilder.createAccount(1,"ACC_001",20F,Constants.AccountType.PRIVATE);
        int accountId = accountDAO.create(account);

        serviceType = objectBuilder.createServiceType(1,70F,"Serv1","Description1");
        serviceType.setServiceType(SERVICE_PHONE);
        int serviceTypeId = serviceTypeDAO.create(serviceType);
    }

    @Test
    @Ignore
    public void dbModifyServicePhone(){
        ServicePhone servicePhone1 = new ServicePhone();
        servicePhone1.setAccount(account);
        servicePhone1.setStatus(Status.ACTIVE);
        servicePhone1.setPhoneNumber("11-22-33");
        servicePhone1.setServiceType(serviceType);
        servicePhone1.setDateStart(getTimestamp());
        servicePhone1.setPeriod(Constants.Period.MONTH);

        ServicePhone servicePhone2 = new ServicePhone();
        servicePhone2.setAccount(account);
        servicePhone2.setStatus(Status.ACTIVE);
        servicePhone2.setPhoneNumber("22-33-44");
        servicePhone2.setServiceType(serviceType);
        servicePhone2.setDateStart(getTimestamp());
        servicePhone2.setPeriod(Constants.Period.WEEK);

        List<Service> servicePhoneList = new ArrayList<Service>();
        servicePhoneList.add(servicePhone1);
        servicePhoneList.add(servicePhone2);

        int servPhoneID1 = serviceDAO.create(servicePhone1);
        int servPhoneID2 = serviceDAO.create(servicePhone2);

        ServicePhone servPhoneBean1 = (ServicePhone)serviceDAO.getById(servPhoneID1);
        ServicePhone servPhoneBean2 = (ServicePhone)serviceDAO.getById(servPhoneID2);

        assertTrue(servPhoneBean1.equals(servicePhone1));
        assertTrue(servPhoneBean2.equals(servicePhone2));
        assertTrue(servicePhoneList.equals(serviceDAO.listServices()));

        serviceDAO.setStatusDelete(servPhoneID2);
        assertFalse(servicePhoneList.equals(serviceDAO.listServices()));

        servicePhone1.setPhoneNumber("55-66-77");
        serviceDAO.update(servicePhone1);
        servPhoneBean1 = (ServicePhone)serviceDAO.getById(servPhoneID1);
        assertTrue(servicePhone1.equals(servPhoneBean1));
    }

}
