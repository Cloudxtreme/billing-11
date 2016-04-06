package com.elstele.bill.test.datasrv;

import com.elstele.bill.dao.interfaces.TariffZoneDAO;
import com.elstele.bill.datasrv.impl.TariffZoneDataServiceImpl;
import com.elstele.bill.domain.TariffZone;
import com.elstele.bill.form.TariffZoneForm;
import com.elstele.bill.reportCreators.dateparser.DateReportParser;
import com.elstele.bill.test.builder.bean.TariffZoneBuilder;
import com.elstele.bill.test.builder.form.TariffZoneFormBuilder;
import com.elstele.bill.utils.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TariffZoneDataServiceTest {

    @Mock
    TariffZoneDAO dao;
    @InjectMocks
    TariffZoneDataServiceImpl dataService;

    private TariffZone tariffZone;
    private TariffZoneForm tariffZoneForm;
    private List<TariffZone> tariffZoneList;
    private List<TariffZoneForm> tariffZoneFormList;

    private Date validFrom;
    private Date validTo;


    @Before
    public void setUp(){
        dataService = new TariffZoneDataServiceImpl();
        MockitoAnnotations.initMocks(this);

        Calendar cal = Calendar.getInstance();
        cal.set(2015, Calendar.JULY, 22, 0, 0, 0);
        validFrom = cal.getTime();
        cal.add(Calendar.YEAR, +1);
        validTo = cal.getTime();

        tariffZone = new TariffZoneBuilder().build()
                .withId(1)
                .withZoneId(10)
                .withTariff(10.3f)
                .withDollar(true)
                .withPrefProfileId(2)
                .withValidFrom(validFrom)
                .withValidTo(validTo)
                .withZoneName("testZone")
                .getRes();

        tariffZoneForm = new TariffZoneFormBuilder().build()
                .withId(1)
                .withZoneId(10)
                .withTariff(10.3f)
                .withDollar(true)
                .withPrefProfileId(2)
                .withValidFrom(validFrom.getTime())
                .withValidTo(validTo.getTime())
                .withValidFromAsDate(validFrom)
                .withValidToAsDate(validTo)
                .withZoneName("testZone")
                .getRes();

        tariffZoneFormList = new ArrayList<>();
        tariffZoneList = new ArrayList<>();

        tariffZoneFormList.add(tariffZoneForm);
        tariffZoneList.add(tariffZone);
    }

    @Test
    public void getTariffZonesListTest(){
        when(dao.getTariffZoneList()).thenReturn(tariffZoneList);

        List<TariffZoneForm> actual = dataService.getTariffZonesList();
        assertTrue(actual.equals(tariffZoneFormList));
    }

    @Test
    public void getOnlyActualTariffZoneListTest(){
        when(dao.getOnlyActualTariffZoneList()).thenReturn(tariffZoneList);

        List<TariffZoneForm> actual = dataService.getOnlyActualTariffZoneList();
        assertTrue(actual.equals(tariffZoneFormList));
    }

    @Test
    public void createTest(){
        dataService.create(tariffZoneForm);
        verify(dao).create(tariffZone);
    }

    @Test
    public void createTestBean(){
        when(dao.create(tariffZone)).thenReturn(1);
        when(dao.getById(1)).thenReturn(tariffZone);
        int result = dataService.create(tariffZone);
        assertTrue(result == 10);
    }

    @Test
    public void deleteZoneTest(){
        String result = dataService.deleteZone(1);
        assertEquals(result, Constants.ZONE_DELETED_SUCCESS);
    }

    @Test
    public void deleteZoneTestNegative(){
        doThrow(Exception.class).when(dao).setStatusDelete(1);
        String result = dataService.deleteZone(1);
        assertEquals(result, Constants.ZONE_DELETED_ERROR);
    }

    @Test
    public void getZoneByIdTest(){
        when(dao.getById(1)).thenReturn(tariffZone);
        TariffZoneForm result = dataService.getZoneById(1);
        assertEquals(result, tariffZoneForm);
    }

    @Test
    public void updateZoneTest(){
        dataService.updateZone(tariffZoneForm);
        verify(dao).update(tariffZone);
    }


    @Test
    public void getUniqueZoneByZoneIdTest(){
        when(dao.getUniqueTariffZoneByZoneID(tariffZone.getZoneId())).thenReturn(tariffZone);
        TariffZone result = dataService.getUniqueZoneByZoneId(tariffZone.getZoneId());
        assertEquals(result, tariffZone);
    }

    @Test
    public void getZoneByNameAndValidFromTest(){
        when(dao.getZoneByNameAndValidFrom(tariffZone.getZoneName(), validFrom)).thenReturn(tariffZone);
        TariffZone result = dataService.getZoneByNameAndValidFrom(tariffZone.getZoneName(), validFrom);
        assertEquals(result, tariffZone);
    }

    @Test
    public void setValidToDateForZonesTest(){
        when(dao.setValidToDateForZones(validTo, DateReportParser.getPrevDayDate(validTo))).thenReturn(10);
        int result = dataService.setValidToDateForZones(validTo);
        assertEquals(result,10);
    }

    @Test
    public void getZoneMapFromDBByDateTest(){
        HashMap<String, TariffZone> tariffZoneHashMap = new HashMap<>();
        tariffZoneHashMap.put(tariffZone.getZoneName(), tariffZone);

        when(dao.getTariffZoneByValidFromDate(validFrom)).thenReturn(tariffZoneList);

        HashMap<String, TariffZone> result = dataService.getZoneMapFromDBByDate(validFrom);
        assertEquals(result, tariffZoneHashMap);
    }
}
