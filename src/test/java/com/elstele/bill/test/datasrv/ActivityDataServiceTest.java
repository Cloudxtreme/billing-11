package com.elstele.bill.test.datasrv;

import com.elstele.bill.dao.impl.UserActivityDAOImpl;
import com.elstele.bill.datasrv.impl.ActivityDataServiceImpl;
import com.elstele.bill.domain.Activity;
import com.elstele.bill.form.ActivityForm;
import com.elstele.bill.test.builder.bean.ActivityBuilder;
import com.elstele.bill.test.builder.form.ActivityFormBuilder;
import org.junit.After;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ActivityDataServiceTest {

    private List<Activity> activityListSample = new ArrayList<Activity>();
    private Activity activitySample;

    @Mock
    private UserActivityDAOImpl activityDAO;
    @InjectMocks
    private ActivityDataServiceImpl activityDataService;

    @Before
    public void setUp() {
        ActivityBuilder ab = new ActivityBuilder();
        activitySample = ab.build().withId(1).withName("Name 1").withDescription("Description 1").getRes();
        Activity activity2 = ab.build().withId(2).withName("Name 2").withDescription("Description 2").getRes();

        activityListSample.add(activitySample);
        activityListSample.add(activity2);
    }

    @After
    public void tearDown() {
        activityDAO = null;
        activityDataService = null;
    }

    @Test
    public void a_listActivity(){
        when(activityDAO.listActivity()).thenReturn(activityListSample);

        ActivityFormBuilder afb = new ActivityFormBuilder();
        ActivityForm acForm1 = afb.build().withId(1).withName("Name 1").withDescription("Description 1").getRes();
        ActivityForm acForm2 = afb.build().withId(2).withName("Name 2").withDescription("Description 2").getRes();

        List<ActivityForm> list = activityDataService.listActivity();
        assertTrue(list.contains(acForm1));
        assertTrue(list.contains(acForm2));
        assertTrue(list.size()==2);

    }

    @Test
    public void b_getActivityFormById(){
        when(activityDAO.getById(1)).thenReturn(activitySample);

        ActivityFormBuilder afb = new ActivityFormBuilder();
        ActivityForm acForm = afb.build().withId(1).withName("Name 1").withDescription("Description 1").getRes();

        ActivityForm formFromDS = activityDataService.getActivityFormById(1);
        assertTrue(acForm.equals(formFromDS));
    }
}
