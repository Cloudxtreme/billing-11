package com.elstele.bill.test.assembler;

import com.elstele.bill.assembler.ActivityAssembler;
import com.elstele.bill.domain.Activity;
import com.elstele.bill.form.ActivityForm;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
public class ActivityAssemblerTest {
        private Activity activity;
        private ActivityForm activityForm;
        private ActivityAssembler assembler;

        @Before
        public void setUp() {
            assembler = new ActivityAssembler();

            activity = new Activity();
            activity.setId(1);
            activity.setName("name");

            activityForm = new ActivityForm();
            activityForm.setId(1);
            activityForm.setName("name");
        }

        @After
        public void tearDown() {
            assembler = null;
            activity = null;
            activityForm = null;
        }

        @Test
        public void fromBeanToFormTest(){
            ActivityForm actual = assembler.fromBeanToForm(activity);
            assertEquals(actual.getId(), activity.getId());
            assertEquals(actual.getName(), activity.getName());
        }

        @Test
        public void fromFormToBeanTest(){
            Activity actual = assembler.fromFormToBean(activityForm);
            assertEquals(actual.getId(), activity.getId());
            assertEquals(actual.getName(), activity.getName());
        }
}
