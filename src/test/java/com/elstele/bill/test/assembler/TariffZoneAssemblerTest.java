package com.elstele.bill.test.assembler;

import com.elstele.bill.assembler.TariffZoneAssembler;
import com.elstele.bill.domain.TariffZone;
import com.elstele.bill.form.TariffZoneForm;
import com.elstele.bill.test.builder.bean.TariffZoneBuilder;
import com.elstele.bill.test.builder.form.TariffZoneFormBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
public class TariffZoneAssemblerTest {
    @InjectMocks
    TariffZoneAssembler assembler;

    private TariffZone bean;
    private TariffZoneForm form;

    @Before
    public void setUp(){
        assembler = new TariffZoneAssembler();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        Date validFrom = cal.getTime();
        cal.add(Calendar.YEAR, +1);
        Date validTo = cal.getTime();

        bean= new TariffZoneBuilder().build()
                .withId(1)
                .withDollar(true)
                .withPrefProfileId(10)
                .withValidTo(validTo)
                .withValidFrom(validFrom)
                .withAdditionalKode("0001")
                .withTariff(92.1f)
                .withTarifPref(991.1f)
                .getRes();

        form= new TariffZoneFormBuilder().build()
                .withId(1)
                .withDollar(true)
                .withPrefProfileId(10)
                .withValidTo(validTo)
                .withValidFrom(validFrom)
                .withAdditionalKode("0001")
                .withTariff(92.1f)
                .withTarifPref(991.1f)
                .getRes();
    }

    @Test
    public void fromFromToBeanTest(){
        TariffZone actual = assembler.fromFormToBean(form);
        assertEquals(actual, bean);
    }

    @Test
    public void fromBeanToForm(){
        TariffZoneForm actual = assembler.fromBeanToForm(bean);
        assertEquals(actual, form);
    }
}
