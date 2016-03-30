package com.elstele.bill.test.dao;

import com.elstele.bill.dao.impl.TariffZoneDAOImpl;
import com.elstele.bill.domain.TariffZone;
import com.elstele.bill.test.builder.bean.TariffZoneBuilder;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
@TransactionConfiguration
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TariffZoneDAOTest {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    TariffZoneDAOImpl tariffZoneDAO;

    private TariffZone tariffZone;
    private TariffZone tariffZone1;
    private TariffZone tariffZone2;
    private List<TariffZone> tariffZoneList;
    private Date validFrom;
    private Date validTo;


    @Before
    public void setUp() {
        String hql = "delete from TariffZone ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.executeUpdate();

        Calendar cal = Calendar.getInstance();
        cal.set(2015, Calendar.JULY, 22, 0, 0, 0);
        validFrom = cal.getTime();
        cal.add(Calendar.YEAR, +1);
        validTo = cal.getTime();

        tariffZone = new TariffZoneBuilder().build()
                .withZoneId(10)
                .withTariff(10.3f)
                .withDollar(true)
                .withPrefProfileId(2)
                .withValidFrom(validFrom)
                .withValidTo(validTo)
                .withZoneName("testZone")
                .getRes();

        tariffZone1 = new TariffZoneBuilder().build()
                .withZoneId(20)
                .withDollar(false)
                .withPrefProfileId(155)
                .withZoneName("testZone111")
                .getRes();
        tariffZone2 = new TariffZoneBuilder().build()
                .withZoneId(222)
                .withDollar(false)
                .withPrefProfileId(181)
                .withValidFrom(validFrom)
                .withZoneName("testZone1221")
                .getRes();

        int id = tariffZoneDAO.create(tariffZone);
        int id1 = tariffZoneDAO.create(tariffZone1);
        int id2 = tariffZoneDAO.create(tariffZone2);
        tariffZone.setId(id);
        tariffZone1.setId(id1);
        tariffZone2.setId(id2);

        tariffZoneList = new ArrayList<>();
        tariffZoneList.add(tariffZone);
        tariffZoneList.add(tariffZone1);
        tariffZoneList.add(tariffZone2);
    }

    @Test
    public void getTariffZoneByZoneIDTest() {
        List<TariffZone> result = tariffZoneDAO.getTariffZoneByZoneID(tariffZone.getZoneId());
        List<TariffZone> expected = new ArrayList<>();
        expected.add(tariffZone);
        assertEquals(result, expected);
    }

    @Test
    public void getUniqueTariffZoneByZoneIDTest(){
        TariffZone result = tariffZoneDAO.getUniqueTariffZoneByZoneID(tariffZone.getZoneId());
        assertEquals(tariffZone, result);
    }

    @Test
    public void getTariffZoneListTest(){
        List<TariffZone> result = tariffZoneDAO.getTariffZoneList();
        assertEquals(result, tariffZoneList);
    }

    @Test
    public void getOnlyActualTariffZoneListTest(){
        tariffZoneList.remove(tariffZone1);
        List<TariffZone> result = tariffZoneDAO.getOnlyActualTariffZoneList();
        assertEquals(result, tariffZoneList);
    }

    @Test
    public void getZoneByNameAndValidFromTest(){
        TariffZone result = tariffZoneDAO.getZoneByNameAndValidFrom(tariffZone.getZoneName(), validFrom);
        assertEquals(result, tariffZone);
    }

    @Test
    public void setValidToDateForZonesTest(){
        int result = tariffZoneDAO.setValidToDateForZones(validTo, validTo);
        assertTrue(result == 2);
    }

    @Test
    public void getTariffZoneByValidFromDateTest(){
        List<TariffZone> result = tariffZoneDAO.getTariffZoneByValidFromDate(validFrom);
        tariffZoneList.remove(tariffZone1);
        assertEquals(result, tariffZoneList);
    }

}
