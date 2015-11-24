package com.elstele.bill.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.elstele.bill.dao.interfaces.USDRateDAO;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class USDRate{
    private USDRateDAO usdRateDAO;
    public static final String ACCESS_KEY = "1c52eaacc4d58ca4f93eb8740c820272";
    public static final String BASE_URL = "http://apilayer.net/api/";
    public static final String ENDPOINT = "live";
    final static Logger log = org.apache.logging.log4j.LogManager.getLogger(USDRate.class);
    static CloseableHttpClient httpClient = HttpClients.createDefault();

    public USDRate(USDRateDAO usdRateDAO) {
        this.usdRateDAO = usdRateDAO;
    }

    public void sendLiveRequest(){
        HttpGet get = new HttpGet(BASE_URL + ENDPOINT + "?access_key=" + ACCESS_KEY);
        try {
            CloseableHttpResponse response =  httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));
            Date timeStampDate = new Date((exchangeRates.getLong("timestamp")*1000));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(timeStampDate);
            Date rateDate = dateFormat.parse(formattedDate);
            Double value = exchangeRates.getJSONObject("quotes").getDouble("USDUAH");
            usdRateDAO.setUSDRateValue(rateDate, value);
            response.close();
            log.info("success, Method = sendLiveRequest");
        } catch (ClientProtocolException e) {
            log.error(e + " Method sendLiveRequest");
        } catch (IOException e) {
            log.error(e + " Method sendLiveRequest");
        } catch (ParseException e) {
            log.error(e + " Method sendLiveRequest");
        } catch (JSONException e) {
            log.error(e + " Method sendLiveRequest");
        } catch (java.text.ParseException e) {
            log.error(e + " Method sendLiveRequest");
        }
    }
}
