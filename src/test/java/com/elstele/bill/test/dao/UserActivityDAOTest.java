package com.elstele.bill.test.dao;

import com.elstele.bill.dao.impl.UserActivityDAOImpl;
import com.elstele.bill.domain.Activity;
import com.elstele.bill.test.builder.ActivityBuilder;
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
public class UserActivityDAOTest {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private UserActivityDAOImpl userActivityDAO;

    private Activity activity1;
    private Activity activity2;

    @Before
    public void setUp() {
        String hql = String.format("delete from Activity");
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.executeUpdate();

        ActivityBuilder ab = new ActivityBuilder();
        activity1 = ab.build().randomActivity().getRes();
        activity2 = ab.build().randomActivity().getRes();
    }

    @Test
    public void a_createActivity(){
        Integer id1 = userActivityDAO.create(activity1);
        Integer id2 = userActivityDAO.create(activity2);

        Activity bean1 = userActivityDAO.getById(id1);
        Activity bean2 = userActivityDAO.getById(id2);
        Activity bean0 = userActivityDAO.getById(0);

        assertTrue(bean0 == null);
        assertTrue(activity1.equals(bean1));
        assertTrue(activity2.equals(bean2));
    }

    @Test
    public void b_updateActivity(){
        Integer id = userActivityDAO.create(activity1);

        activity1.setName("UpdateName");
        userActivityDAO.update(activity1);
        Activity bean = userActivityDAO.getById(id);
        assertTrue(bean.equals(activity1));
    }

    @Test
    public void c_deleteActivity(){
        Integer id = userActivityDAO.create(activity1);

        userActivityDAO.setStatusDelete(id);
        Activity res = userActivityDAO.getById(id);
        assertTrue(res.getStatus().equals(Status.DELETED));

        assertFalse(res == null);
        userActivityDAO.delete(id);
        assertTrue(userActivityDAO.getById(id) == null);
    }

    @Test
    public void d_activityList() {
        Integer id1 = userActivityDAO.create(activity1);
        Integer id2 = userActivityDAO.create(activity2);

        List<Activity> activityList = userActivityDAO.listActivity();

        assertTrue(activityList.size() == 2);
        assertTrue(activityList.contains(activity1));
        assertTrue(activityList.contains(activity2));

        userActivityDAO.setStatusDelete(activity1);
        List<Activity> activityList2 = userActivityDAO.listActivity();
        assertTrue(activityList2.size() == 1);
    }
}
