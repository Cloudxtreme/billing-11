package com.elstele.bill.interceptors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class ApplicationSessionListener implements HttpSessionListener {
    final static Logger LOGGER = LogManager.getLogger(ApplicationSessionListener.class);

    public void sessionCreated(HttpSessionEvent event) {
        LOGGER.info("Session Created ");
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        LOGGER.info("Session destroyed ");
    }
}
