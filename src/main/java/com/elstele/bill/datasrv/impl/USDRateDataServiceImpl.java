package com.elstele.bill.datasrv.impl;

import com.elstele.bill.dao.interfaces.USDRateDAO;
import com.elstele.bill.datasrv.interfaces.USDRateDataService;
import com.elstele.bill.reportCreators.dateparser.DateReportParser;
import com.elstele.bill.utils.USDRateHelper;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;


@Service
public class USDRateDataServiceImpl implements USDRateDataService {

    @Autowired
    USDRateDAO usdRateDAO;
    final static Logger log = org.apache.logging.log4j.LogManager.getLogger(USDRateDataServiceImpl.class);
    private static final String BANKURL = "http://bank-ua.com/export/currrate.xml";

    @Transactional
    public void getXMLUSDRate() {
        String xml = USDRateHelper.crunchifyGetURLContents(BANKURL);
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            Document doc = db.parse(is);
            NodeList nodes = doc.getElementsByTagName("item");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                NodeList name = element.getElementsByTagName("char3");
                Element line = (Element) name.item(0);
                if (USDRateHelper.getCharacterDataFromElement(line).equalsIgnoreCase("USD")) {
                    NodeList title = element.getElementsByTagName("rate");
                    line = (Element) title.item(0);
                    Double value = Double.parseDouble(USDRateHelper.getCharacterDataFromElement(line))/100;
                    usdRateDAO.setUSDRateValue(DateReportParser.getNowDate(), value);
                    log.info("success, Method = sendLiveRequest");
                }
            }
        } catch (ParserConfigurationException e) {
            log.error(e + " Method sendLiveRequest");
        } catch (SAXException e) {
            log.error(e + " Method sendLiveRequest");
        } catch (IOException e) {
            log.error(e + " Method sendLiveRequest");
        }
    }
}
