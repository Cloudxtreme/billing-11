package com.elstele.bill.test.utils;

import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Messagei18nHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class Messagei18nHelperTest {
    @InjectMocks
    Messagei18nHelper helper;
    @Mock
    MessageSource source;
    private Locale locale;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        locale = LocaleContextHolder.getLocale();
    }

    @Test(expected = NoSuchMessageException.class)
    public void getMessageTest(){
        String TEST_MESSAGE_NOT_EXISTS = "test.message.negative";
        doThrow(new NoSuchMessageException("Exception test")).when(source).getMessage(TEST_MESSAGE_NOT_EXISTS, null, locale);
        helper.getMessage(TEST_MESSAGE_NOT_EXISTS);
    }

    @Test
    public void getMessageTestPositive(){
        String TEST_MESSAGE = "test.message";
        when(source.getMessage(TEST_MESSAGE, null, locale)).thenReturn("Тестовое сообщение");
        String actual = helper.getMessage(TEST_MESSAGE);
        assertEquals(actual, "Тестовое сообщение");
    }

    @Test
    public void getTypeMessageTest(){
        String actual = helper.getTypeMessage("success");
        assertEquals(actual, Constants.SUCCESS_MESSAGE);
    }
}
