package com.elstele.bill.test.dao;

import com.elstele.bill.dao.impl.StreetDAOImpl;
import com.elstele.bill.domain.Street;
import com.elstele.bill.test.builder.bean.StreetBuilder;
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
@ContextConfiguration("classpath:test-servlet-context.xml")
@TransactionConfiguration
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StreetDAOTest {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private StreetDAOImpl dao;

    private Street street;
    private Street street1;
    private Integer id;
    private Integer id1;

    @Before
    public void setUp(){
        StreetBuilder builder = new StreetBuilder();
        street = builder.build().withRandomName().getRes();
        street1 = builder.build().withRandomName().getRes();
        id = dao.create(street);
        id1 = dao.create(street1);
    }

    @Test
    public void getListOfStreetsTest(){
        List<Street> streetList = dao.getListOfStreets();
        assertTrue(streetList.contains(street));
        assertTrue(streetList.contains(street1));
    }

    @Test
    public void getStreetIDByStreetNameTest(){
        Integer actualId = dao.getStreetIDByStreetName(street.getName());
        Integer actualId1 = dao.getStreetIDByStreetName(street1.getName());

        assertEquals(actualId, id);
        assertEquals(actualId1, id1);
    }


}
