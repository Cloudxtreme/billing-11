package com.elstele.bill.test.dao;

import com.elstele.bill.dao.impl.DirectionDAOImpl;
import com.elstele.bill.domain.Direction;
import com.elstele.bill.domain.TariffZone;
import com.elstele.bill.test.builder.bean.DirectionBuilder;
import com.elstele.bill.utils.Constants;
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
public class DirectionDAOTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    DirectionDAOImpl directionDAO;

    private Direction direction;
    private List<Direction> directionList;
    private final String DIRECTION_PREFIX = "0097";
    private Date validFrom;
    private Date validTo;
    private int directionId;

    @Before
    public void setUp(){
        String hql = "delete from Direction";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.executeUpdate();

        Calendar cal = Calendar.getInstance();
        cal.set(2015, Calendar.JULY, 5, 0, 0, 0);
        validFrom = cal.getTime();
        cal.add(Calendar.YEAR, +1);
        validTo = cal.getTime();

        TariffZone tariffZone = new TariffZone();
        tariffZone.setZoneId(11);
        tariffZone.setId(1);

        direction = new DirectionBuilder().build()
                .withPrefix(DIRECTION_PREFIX)
                .withTariffZone(11)
                .withValidFromDate(validFrom)
                .withValidToDate(validTo)
                .withDescription("Test")
                .getRes();
        directionId = directionDAO.create(direction);
        direction.setId(directionId);

        directionList = new ArrayList<>();
        directionList.add(direction);
    }

    @Test
    public void getDirectionListTest(){
        List<Direction> expectedList = directionDAO.getDirectionList(0, 1, DIRECTION_PREFIX);
        assertTrue(expectedList.equals(directionList));
    }

    @Test
    public void getPagesCountTest(){
        int actual = directionDAO.getPagesCount(DIRECTION_PREFIX);
        assertTrue(actual == 1);
    }

    @Test
    public void getByPrefixMainPartTest(){
        Direction actual = directionDAO.getByPrefixMainPart("97");
        assertEquals(actual, direction);
    }

    @Test
    public void getDirectionByPrefixAndDateTest(){
        Direction actual = directionDAO.getDirectionByPrefixAndDate(DIRECTION_PREFIX, validFrom);
        assertEquals(actual, direction);
    }
    
    @Test
    public void getDirectionForDateCorrectingTest(){
        Direction actual = directionDAO.getDirectionForDateCorrecting(DIRECTION_PREFIX, validTo, Constants.SMALLER);
        assertEquals(actual, direction);
    }

    @Test
    public void setValidToDateForDirectionsTest(){
        direction.setValidTo(null);
        directionDAO.update(direction);

        int actual = directionDAO.setValidToDateForDirections(validTo, validTo);
        assertTrue(actual == 1);
    }

    @Test
    public void getDirectionListByValidFromDateTest(){
        List<Direction> actual = directionDAO.getDirectionListByValidFromDate(validFrom);
        assertEquals(actual, directionList);
    }
}
