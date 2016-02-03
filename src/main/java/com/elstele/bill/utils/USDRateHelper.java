package com.elstele.bill.utils;

import org.apache.logging.log4j.Logger;
import org.w3c.dom.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class USDRateHelper {
    final static Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger(USDRateHelper.class);

    public static String crunchifyGetURLContents(String myURL) {
        LOGGER.info("crunchifyGetURLContents() is trying to connect to : " + myURL);
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn;
        InputStreamReader in = null;
        try {
            URL url = new URL(myURL);
            urlConn = url.openConnection();
            if (urlConn != null)
                urlConn.setReadTimeout(60 * 1000);
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(), Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                int cp;
                while ((cp = bufferedReader.read()) != -1) {
                    sb.append((char) cp);
                }
                bufferedReader.close();
            }
            if (in != null) {
                in.close();
            }
            LOGGER.info("Connection successful. Method = crunchifyGetURLContents");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return sb.toString();
    }

    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }
}
