package com.elstele.bill.test.datasrv;


import com.elstele.bill.dao.interfaces.StreetDAO;
import com.elstele.bill.datasrv.impl.StreetDataServiceImpl;
import com.elstele.bill.domain.Street;
import com.elstele.bill.Builders.bean.StreetBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class StreetDataServiceTest {

    @Mock
    StreetDAO streetDAO;

    @InjectMocks
    StreetDataServiceImpl streetDataService;

    private List<Street> streetList;
    private Street street;

    @Before
    public void setUp(){
        streetDataService = new StreetDataServiceImpl();
        MockitoAnnotations.initMocks(this);
        StreetBuilder streetBuilder = new StreetBuilder();

        streetList = new ArrayList<>();

        street = streetBuilder.build().withName("Abcd").withId(1).getRes();
        Street street1 = streetBuilder.build().withName("Zwkl").withId(2).getRes();

        streetList.add(street);
        streetList.add(street1);
    }

    @Test
    public void getListOfStreetsTest(){
        when(streetDAO.getListOfStreets()).thenReturn(streetList);
        List<Street> actualList = streetDataService.getListOfStreets("Ab");
        assertTrue(actualList.contains(street));
    }
}
