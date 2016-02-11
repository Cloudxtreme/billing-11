package com.elstele.bill.test.datasrv;

import com.elstele.bill.dao.impl.UserRoleDAOImpl;
import com.elstele.bill.datasrv.impl.UserRoleDataServiceImpl;
import com.elstele.bill.domain.Activity;
import com.elstele.bill.domain.UserRole;
import com.elstele.bill.form.UserRoleForm;
import com.elstele.bill.Builders.bean.ActivityBuilder;
import com.elstele.bill.Builders.bean.UserRoleBuilder;
import com.elstele.bill.Builders.form.UserRoleFormBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserRoleDataServiceTest {
    @Mock
    private UserRoleDAOImpl userRoleDAO;
    @InjectMocks
    private UserRoleDataServiceImpl userRoleDataService;

    private UserRole beanSample;
    private UserRoleForm formSample1;
    private UserRoleForm formSample2;
    private List<UserRole> listSample;

    @Before
    public void setUp() {
        getBeanSetUp();
        getFormSetUp();
    }

    @Test
    public void a_getUserRoleFormById(){
        when(userRoleDAO.getById(10)).thenReturn(beanSample);

        UserRoleForm transForm = userRoleDataService.getUserRoleFormById(10);
        assertTrue(transForm.equals(formSample1));
    }

    @Test
    public void b_listUserRole(){
        when(userRoleDAO.listUserRole()).thenReturn(listSample);

        List<UserRoleForm> userRoleFormList = userRoleDataService.listUserRole();
        assertTrue(userRoleFormList.contains(formSample1));
        assertTrue(userRoleFormList.contains(formSample2));
        assertTrue(userRoleFormList.size()==2);
    }

    private void getBeanSetUp(){
        ActivityBuilder ab = new ActivityBuilder();
        Activity activity1 = ab.build().withId(2).withName("Activity #1").getRes();
        Activity activity2 = ab.build().withId(3).withName("Activity #2").withDescription("Description").getRes();
        List<Activity> acList = new ArrayList<>();
        acList.add(activity1);

        UserRoleBuilder urb = new UserRoleBuilder();
        beanSample = urb.build().withId(10).withName("Name 1").withDescription("Description 1").withActivities(acList).getRes();
        acList.add(activity2);
        UserRole userRole2 = urb.build().withId(11).withName("Name 2").withDescription("Description 2").withActivities(acList).getRes();

        listSample = new ArrayList<>();
        listSample.add(beanSample);
        listSample.add(userRole2);
    }

    private void getFormSetUp(){
        List<Integer> acListId = new ArrayList<>();
        acListId.add(2);

        List<String> acListName = new ArrayList<>();
        acListName.add("Activity1");

        UserRoleFormBuilder urfb = new UserRoleFormBuilder();
        formSample1 = urfb.build().withId(10).withName("Name 1").withDescription("Description 1").withActivityId(acListId).withActivityName(acListName).getRes();
        acListId.add(3);
        acListName.add("Activity2");
        formSample2 = urfb.build().withId(11).withName("Name 2").withDescription("Description 2").withActivityId(acListId).withActivityName(acListName).getRes();
    }
}
