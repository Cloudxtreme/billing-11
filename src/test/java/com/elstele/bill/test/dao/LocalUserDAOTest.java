package com.elstele.bill.test.dao;

import com.elstele.bill.dao.LocalUserDAO;
import com.elstele.bill.dao.LocalUserDAOImpl;
import com.elstele.bill.datasrv.LocalUserDataServiceImpl;
import com.elstele.bill.domain.LocalUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/test-servlet-context.xml")
@TransactionConfiguration
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalUserDAOTest {

    private LocalUser localUser;

    @Autowired
    private LocalUserDAOImpl dao;

    @Before
    public void setUp(){
        localUser = new LocalUser();
        localUser.setUsername("John Snow");
        localUser.setPassword("Wall");

    }

    @Test
    public void test1_letsStoreLocalUserAndDeleteId(){
        localUser = new LocalUser();
        localUser.setUsername("John Snow");
        localUser.setPassword("Wall");
        dao.save(localUser);
        assertTrue(localUser.getId() != null);

        Integer id = localUser.getId();
        dao.delete(id);

        LocalUser bean = dao.getById(id);
        assertTrue(bean == null);




    }

    @Test
    public void test2_letsGetLocalUserByIdAndModifyThem(){
        localUser = new LocalUser();
        localUser.setUsername("John Snow");
        localUser.setPassword("Wall");
        dao.save(localUser);

        Integer id = localUser.getId();
        LocalUser bean = dao.getById(id);
        assertEquals(bean, localUser);
    }

    @Test
    public void test3_checkUserByNameAndPass(){
        localUser = new LocalUser();
        localUser.setUsername("John Snow");
        localUser.setPassword("Wall");
        dao.save(localUser);

        LocalUser lu1 = dao.getLocalUserByNameAndPass("Tireon", "wine");
        assertNull(lu1);

        LocalUser lu2 = dao.getLocalUserByNameAndPass("John Snow", "Wall");
        assertNotNull(lu2);

    }




}
