package com.elstele.bill.test.interceptors;


import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.interceptors.AuthenticationInterceptor;
import com.elstele.bill.utils.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationInterceptorTest {
    @InjectMocks
    AuthenticationInterceptor interceptor;
    @Mock
    HttpServletRequest servletRequest;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void preHandleTest() throws Exception {
        String PATH_TEST = "test/path";
        LocalUser user = new LocalUser();
        user.setUsername("test");
        user.setPassword("password");

        when(servletRequest.getRequestURI()).thenReturn(PATH_TEST);
        when(servletRequest.getContextPath()).thenReturn("test");
        when(servletRequest.getSession()).thenReturn(session);
        when(session.getAttribute(Constants.LOCAL_USER)).thenReturn(user);
        boolean result = interceptor.preHandle(servletRequest, response, new Object());

        assertTrue(result);

        when(session.getAttribute(Constants.LOCAL_USER)).thenReturn(null);
        result = interceptor.preHandle(servletRequest, response, new Object());
        assertFalse(result);

        when(session.getAttribute(Constants.LOCAL_USER)).thenReturn(null);
        when(servletRequest.getContextPath()).thenReturn("");
        result = interceptor.preHandle(servletRequest, response, new Object());
        assertFalse(result);
    }
}
