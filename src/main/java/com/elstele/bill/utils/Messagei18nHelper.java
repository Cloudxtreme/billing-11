package com.elstele.bill.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

@Service
public class Messagei18nHelper {
    @Autowired
    private MessageSource messageSource;

    public String getMessage(String code){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, null, locale);
    }

    public String getTypeMessage(String message){
        if(message.contains("success")){
            return Constants.SUCCESS_MESSAGE;
        }else{
            return Constants.ERROR_MESSAGE;
        }
    }
}
