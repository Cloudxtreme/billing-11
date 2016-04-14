package com.elstele.bill.test.datasrv;


import com.elstele.bill.assembler.DirectionAssembler;
import com.elstele.bill.dao.interfaces.DirectionDAO;
import com.elstele.bill.dao.interfaces.TariffZoneDAO;
import com.elstele.bill.datasrv.impl.DirectionDataServiceImpl;
import com.elstele.bill.domain.Direction;
import com.elstele.bill.domain.TariffZone;
import com.elstele.bill.form.DirectionForm;
import com.elstele.bill.form.TariffZoneForm;
import com.elstele.bill.reportCreators.dateparser.DateReportParser;
import com.elstele.bill.test.builder.bean.DirectionBuilder;
import com.elstele.bill.test.builder.bean.TariffZoneBuilder;
import com.elstele.bill.test.builder.form.DirectionFormBuilder;
import com.elstele.bill.test.builder.form.TariffZoneFormBuilder;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.postgresql.util.PSQLException;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class DirectionDataServiceTest {
    @Mock
    TariffZoneDAO tariffZoneDAO;
    @Mock
    DirectionDAO directionDAO;
    @Mock
    DirectionAssembler assembler;
    @InjectMocks
    DirectionDataServiceImpl directionDataService;

    private List<Direction> directionList;
    private List<DirectionForm> directionFormList;
    private Direction direction;
    private DirectionForm directionForm;
    private Date validFrom;
    private Date validTo;

    private List<TariffZoneForm> tariffZoneFormList;
    private List<TariffZone> tariffZoneList;
    private TariffZone tariffZone;

    private final String DIRECTION_PREFIX = "097";

    @Before
    public void setUp(){
        directionDataService = new DirectionDataServiceImpl();
        MockitoAnnotations.initMocks(this);

        Calendar cal = Calendar.getInstance();
        cal.set(2015, Calendar.JULY, 5, 0, 0, 0);
        validFrom = cal.getTime();
        cal.add(Calendar.YEAR, -1);
        validTo = cal.getTime();

        tariffZoneFormList = new ArrayList<>();
        TariffZoneForm tariffZoneForm = new TariffZoneFormBuilder().build()
                .withZoneId(11)
                .withId(10)
                .withDollar(true)
                .withZoneName("Zone1")
                .withValidFrom(validFrom.getTime())
                .withValidTo(validTo.getTime())
                .withValidFromAsDate(validFrom)
                .withValidToAsDate(validTo)
                .getRes();

        tariffZoneFormList.add(tariffZoneForm);

        tariffZoneList = new ArrayList<>();
        tariffZone = new TariffZoneBuilder().build()
                .withZoneId(11)
                .withZoneName("Zone1")
                .withDollar(true)
                .withId(10)
                .withValidFrom(validFrom)
                .withValidTo(validTo)
                .getRes();

        tariffZoneList.add(tariffZone);



        direction = new DirectionBuilder().build()
                .withId(1)
                .withDescription("test")
                .withPrefix(DIRECTION_PREFIX)
                .withTariffZone(11)
                .withValidFromDate(validFrom)
                .withValidToDate(validTo)
                .getRes();

        directionForm = new DirectionFormBuilder().build()
                .withId(1)
                .withDescription("test")
                .withPrefix(DIRECTION_PREFIX)
                .withTariffZoneList(tariffZoneFormList)
                .withValidFromDate(validFrom.getTime())
                .withValidToDate(validTo.getTime())
                .getRes();

        directionList = new ArrayList<>();
        directionList.add(direction);

        directionFormList = new ArrayList<>();
        directionFormList.add(directionForm);
    }

    @Test
    public void getDirectionListTest(){
        when(directionDAO.getDirectionList(50, 10, DIRECTION_PREFIX)).thenReturn(directionList);
        when(tariffZoneDAO.getTariffZoneByZoneID(direction.getTarifZone())).thenReturn(tariffZoneList);
        List<DirectionForm> actualList = directionDataService.getDirectionList(6, 10, DIRECTION_PREFIX);
        assertTrue(actualList.equals(directionFormList));
    }

    @Test
    public void getPagesCountTest(){
        when(directionDAO.getPagesCount(DIRECTION_PREFIX)).thenReturn(100);
        int actualPagesCount = directionDataService.getPagesCount(10, DIRECTION_PREFIX);
        assertTrue(actualPagesCount == 100/10);
    }

    @Test
    public void deleteDirectionTest(){
        String actualResult = directionDataService.deleteDirection(1);
        assertTrue(actualResult.equals(Constants.DIRECTION_DELETE_SUCCESS));
    }

    @Test(expected = Exception.class)
    public void deleteDirectionTestNegative(){
        doThrow(new Exception()).when(directionDAO).setStatusDelete(1);
        directionDataService.deleteDirection(1);
    }

    @Test
    public void createDirectionTest(){
        when(tariffZoneDAO.getById(0)).thenReturn(tariffZone);
        directionDataService.createDirection(directionForm);
    }

    @Test
    public void createDirectionBeanTest() throws PSQLException {
        when(directionDAO.create(direction)).thenReturn(1);
        int actualId = directionDataService.createDirection(direction);
        assertTrue(actualId == 1);
    }

    @Test
    public void getDirectionByIdTest(){
        when(directionDAO.getById(1)).thenReturn(direction);
        when(tariffZoneDAO.getTariffZoneByZoneID(direction.getTarifZone())).thenReturn(tariffZoneList);
        DirectionForm result = directionDataService.getDirectionById(1);
        assertTrue(result.equals(directionForm));
    }

    @Test
    public void updateDirectionTest(){
        when(tariffZoneDAO.getById(directionForm.getZoneId())).thenReturn(tariffZone);
        directionDataService.updateDirection(directionForm);
        verify(directionDAO).update(direction);
    }

    @Test
    public void checkForFreeTest(){
        when(directionDAO.getDirectionByPrefixAndDate(DIRECTION_PREFIX, direction.getValidFrom())).thenReturn(direction);
        ResponseToAjax actual = directionDataService.checkForFree(directionForm.getId(), DIRECTION_PREFIX, directionForm.getValidFrom());
        assertEquals(actual, ResponseToAjax.FREE);

        actual = directionDataService.checkForFree(directionForm.getId(), DIRECTION_PREFIX, directionForm.getValidTo());
        assertEquals(actual, ResponseToAjax.FREE);

        actual = directionDataService.checkForFree(99, DIRECTION_PREFIX, directionForm.getValidFrom());
        assertEquals(actual, ResponseToAjax.BUSY);

        actual = directionDataService.checkForFree(0, DIRECTION_PREFIX, directionForm.getValidFrom());
        assertEquals(actual, ResponseToAjax.BUSY);
    }

    @Test
    public void getByPrefixMainPartTest(){
        when(directionDAO.getByPrefixMainPart(DIRECTION_PREFIX)).thenReturn(direction);
        Direction result = directionDataService.getByPrefixMainPart(DIRECTION_PREFIX);
        assertEquals(result, direction);
    }

    @Test
    public void getDirectionByPrefixAndDateTest(){
        when(directionDAO.getDirectionByPrefixAndDate(DIRECTION_PREFIX, direction.getValidFrom())).thenReturn(direction);
        Direction result = directionDataService.getDirectionByPrefixAndDate(DIRECTION_PREFIX, direction.getValidFrom());
        assertEquals(result, direction);
    }

    @Test
    public void setValidToDateForDirectionsTest(){
        when(directionDAO.setValidToDateForDirections(validTo, DateReportParser.getPrevDayDate(validTo))).thenReturn(1000);
        int changedCounts = directionDataService.setValidToDateForDirections(validTo);
        assertEquals(changedCounts, 1000);
    }

    @Test
    public void getDirectionMapByValidFromDateTest(){
        when(directionDAO.getDirectionListByValidFromDate(validFrom)).thenReturn(directionList);
        HashMap<String, Direction> expected = new HashMap<>();
        expected.put(direction.getPrefix(), direction);

        HashMap<String, Direction> actual = directionDataService.getDirectionMapByValidFromDate(validFrom);

        assertEquals(actual, expected);
    }

}
