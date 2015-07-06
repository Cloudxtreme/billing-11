package com.elstele.bill.test.datasrv;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.elstele.bill.dao.LocalUserDAOImpl;
import com.elstele.bill.datasrv.LocalUserDataServiceImpl;
import com.elstele.bill.domain.LocalUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LocalUserDataServiceTest  {

    @Mock
    private LocalUserDAOImpl localUserDAO;

    @InjectMocks
    LocalUserDataServiceImpl localUserDataService;

    @Before
    public void setUp(){
        localUserDataService = new LocalUserDataServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        localUserDataService = null;
        localUserDAO = null;
    }

    @Test
    public void checkForValidCredential(){
        LocalUser lu = new LocalUser();
        lu.setUsername("John Snow");
        lu.setPassword("secret");
        lu.setId(1);
        when(localUserDAO.getLocalUserByNameAndPass("John Snow", "secret")).thenReturn(lu);
        assertTrue(localUserDataService.isCredentialValid("John Snow", "secret"));
    }

    @Test
    public void checkForInvalidCredential(){
        LocalUser lu = new LocalUser();
        lu.setUsername("John Snow");
        lu.setPassword("secret");
        lu.setId(1);
        when(localUserDAO.getLocalUserByNameAndPass("John Snow", "secret")).thenReturn(lu);
        assertFalse(localUserDataService.isCredentialValid("Tireon Lanister", "secret"));
    }

    @Test
    public void getLocalUserByRightCredentials(){
        LocalUser lu = new LocalUser();
        lu.setUsername("John Snow");
        lu.setPassword("secret");
        lu.setId(1);
        when(localUserDAO.getLocalUserByNameAndPass("John Snow", "secret")).thenReturn(lu);
        assertEquals(localUserDataService.getUserByNameAndPass("John Snow", "secret"), lu);
    }

    @Test
    public void getLocalUserByWrongCredentials(){
        LocalUser lu = new LocalUser();
        lu.setUsername("John Snow");
        lu.setPassword("secret");
        lu.setId(1);
        when(localUserDAO.getLocalUserByNameAndPass("John Snow", "secret")).thenReturn(lu);
        assertNull(localUserDataService.getUserByNameAndPass("Tireon Lanister", "secret"));
    }
}
