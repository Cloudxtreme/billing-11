package com.elstele.bill.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.i18n.LocaleContextHolder;

public class MessageLanguageDeterminant {
    final static Logger log = LogManager.getLogger(MessageLanguageDeterminant.class);

    public static String determine(String methodName) {
        String language = LocaleContextHolder.getLocale().getLanguage();
        if (language.equals("en")) {
            switch (methodName) {
                case "loginCall":
                    return "Your input is incorrect, please try again!";
                case "addDeviceFromForm":
                    return "Device was successfully added.";
                case "UpdateDeviceFromForm":
                    return "Device was successfully updated.";
                case "saveAccountFull":
                    return "Account was updated successfully";
                case "searchAccount":
                    return "Nothing found. Please try again";
                case "serviceDelete":
                    return "Service was successfully deleted.";
                case "serviceAttributeDelete":
                    return "Service Attribute was successfully deleted.";
                default: {
                    log.info("no compares. Method determine");
                    return "No compares";
                }
            }
        } else {
            switch (methodName) {
                case "loginCall":
                    return "Данные неверны. Пожалуйста попробуйте ещё раз!";
                case "addDeviceFromForm":
                    return "Устройство было успешно добавлено!";
                case "UpdateDeviceFromForm":
                    return "Устройство было успешно обновлено!";
                case "saveAccountFull":
                    return "Аккаунт был успешно обновлён";
                case "searchAccount":
                    return "Ничего не найдено. Попробуйте ещё раз";
                case "serviceDelete":
                    return "Сервис был упешно удалён.";
                case "serviceAttributeDelete":
                    return "Атрибут сервисов был упешно удалён.";
                default: {
                    log.info("no compares. Method determine");
                    return "No compares";
                }
            }
        }
    }
}
