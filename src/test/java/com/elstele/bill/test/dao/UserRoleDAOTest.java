package com.elstele.bill.test.dao;

import com.elstele.bill.dao.impl.UserRoleDAOImpl;
import com.elstele.bill.dao.impl.UserActivityDAOImpl;
import com.elstele.bill.domain.Activity;
import com.elstele.bill.domain.UserRole;
import com.elstele.bill.Builders.bean.ActivityBuilder;
import com.elstele.bill.Builders.bean.UserRoleBuilder;
import com.elstele.bill.utils.Enums.Status;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
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

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/test-servlet-context.xml")
@TransactionConfiguration
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserRoleDAOTest {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private UserRoleDAOImpl userRoleDAO;
    @Autowired
    private UserActivityDAOImpl activityDAO;

    private UserRole userRole1;
    private UserRole userRole2;
    @Before
    public void setUp() {
        String sql = String.format("delete from ROLE_ACTIVITY");
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.executeUpdate();
        String hql = String.format("delete from Activity");
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.executeUpdate();
        hql = String.format("delete from UserRole");
        query = sessionFactory.getCurrentSession().createQuery(hql);
        query.executeUpdate();

        ActivityBuilder ab = new ActivityBuilder();
        Activity ac1 = ab.build().randomActivity().getRes();
        Activity ac2 = ab.build().randomActivity().getRes();
        activityDAO.create(ac1);
        activityDAO.create(ac2);

        List<Activity> acList = new ArrayList<Activity>();
        acList.add(ac1);

        UserRoleBuilder urb = new UserRoleBuilder();
        userRole1 = urb.build().randomUserRole().withActivities(acList).getRes();
        acList.add(ac2);
        userRole2 = urb.build().randomUserRole().withActivities(acList).getRes();
    }

    @Test
    public void a_createUserRole(){
        Integer id1 = userRoleDAO.create(userRole1);
        Integer id2 = userRoleDAO.create(userRole2);

        UserRole bean1 = userRoleDAO.getById(id1);
        UserRole bean2 = userRoleDAO.getById(id2);
        UserRole bean0 = userRoleDAO.getById(0);

        assertTrue(bean0 == null);
        assertTrue(userRole1.equals(bean1));
        assertTrue(userRole2.equals(bean2));
    }

    @Test
    public void b_updateUserRole(){
        Integer id = userRoleDAO.create(userRole1);

        userRole1.setName("UpdateName");
        userRoleDAO.update(userRole1);
        UserRole bean = userRoleDAO.getById(id);
        assertTrue(bean.equals(userRole1));
    }

    @Test
    public void c_deleteUserRole(){
        Integer id = userRoleDAO.create(userRole1);

        userRoleDAO.setStatusDelete(id);
        UserRole res = userRoleDAO.getById(id);
        assertTrue(res.getStatus().equals(Status.DELETED));

        assertFalse(res == null);
        userRoleDAO.delete(id);
        assertTrue(userRoleDAO.getById(id) == null);
    }

    @Test
    public void d_userRoleList() {
        Integer id1 = userRoleDAO.create(userRole1);
        Integer id2 = userRoleDAO.create(userRole2);

        List<UserRole> list = userRoleDAO.listUserRole();

        assertTrue(list.size() == 2);
        assertTrue(list.contains(userRole1));
        assertTrue(list.contains(userRole2));

        userRoleDAO.setStatusDelete(userRole1);
        List<UserRole> list2 = userRoleDAO.listUserRole();
        assertTrue(list2.size() == 1);
    }
}

