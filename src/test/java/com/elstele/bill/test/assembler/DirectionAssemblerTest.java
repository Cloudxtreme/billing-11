package com.elstele.bill.test.assembler;

import com.elstele.bill.assembler.DirectionAssembler;
import com.elstele.bill.assembler.TariffZoneAssembler;
import com.elstele.bill.dao.interfaces.TariffZoneDAO;
import com.elstele.bill.domain.Direction;
import com.elstele.bill.domain.TariffZone;
import com.elstele.bill.form.DirectionForm;
import com.elstele.bill.form.TariffZoneForm;
import com.elstele.bill.test.builder.bean.DirectionBuilder;
import com.elstele.bill.test.builder.bean.TariffZoneBuilder;
import com.elstele.bill.test.builder.form.DirectionFormBuilder;
import com.elstele.bill.test.builder.form.TariffZoneFormBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
public class DirectionAssemblerTest {

    @Mock
    TariffZoneAssembler tariffZoneAssembler;
    @Mock
    TariffZoneDAO tariffZoneDAO;
    @InjectMocks
    DirectionAssembler directionAssembler;

    private Direction bean;
    private DirectionForm form;

    private Date validFrom;
    private Date validTo;
    private List<TariffZoneForm> tariffZoneForms;
    private List<TariffZone> tariffZoneBeanList;

    private TariffZoneForm tariffZoneForm;
    private TariffZone tariffZone;

    @Before
    public void setUp(){
        directionAssembler = new DirectionAssembler(tariffZoneDAO);

        Calendar cal = Calendar.getInstance();
        cal.set(2015, Calendar.JULY , 5, 0, 0, 0);
        validFrom = cal.getTime();
        cal.set(Calendar.YEAR, -1);
        validTo = cal.getTime();

        tariffZoneForms = new ArrayList<>();
        tariffZoneForm = new TariffZoneFormBuilder().build()
                .withZoneId(1)
                .withId(10)
                .withValidFrom(validFrom.getTime())
                .withValidTo(validTo.getTime())
                .withValidFromAsDate(validFrom)
                .withValidToAsDate(validTo)
                .getRes();
        tariffZoneForms.add(tariffZoneForm);

        tariffZoneBeanList = new ArrayList<>();
        tariffZone = new TariffZoneBuilder().build()
                .withZoneId(1)
                .withId(10)
                .withValidFrom(validFrom)
                .withValidTo(validTo)
                .getRes();
        tariffZoneBeanList.add(tariffZone);

        bean = new DirectionBuilder().build()
                .withId(1)
                .withDescription("TestDesc")
                .withPrefix("00944")
                .withTariffZone(1)
                .withValidFromDate(validFrom)
                .withValidToDate(validTo)
                .getRes();

        form = new DirectionFormBuilder().build()
                .withId(1)
                .withDescription("TestDesc")
                .withPrefix("00944")
                .withTariffZoneList(tariffZoneForms)
                .withValidFromDate(validFrom.getTime())
                .withValidToDate(validTo.getTime())
                .getRes();
    }

    @Test
    public void fromBeanToForm(){
        when(tariffZoneDAO.getTariffZoneByZoneID(bean.getTarifZone())).thenReturn(tariffZoneBeanList);
        DirectionForm result= directionAssembler.fromBeanToForm(bean);
        assertTrue(result.equals(form));
    }

    @Test
    public void fromFormToBean(){
        when(tariffZoneDAO.getById(form.getZoneId())).thenReturn(tariffZone);
        Direction result = directionAssembler.fromFormToBean(form);
        assertTrue(result.equals(bean));
    }

    @Test(expected = NullPointerException.class)
    public void fromBeanToFormCheckNonExistedZones(){
        when(tariffZoneDAO.getTariffZoneByZoneID(bean.getTarifZone())).thenReturn(null);
        form.setTariffZoneList(null);
        DirectionForm result= directionAssembler.fromBeanToForm(bean);
        assertTrue(result.equals(form));
    }

}
