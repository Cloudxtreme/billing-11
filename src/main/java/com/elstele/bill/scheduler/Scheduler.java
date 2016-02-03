package com.elstele.bill.scheduler;

import com.elstele.bill.datasrv.interfaces.USDRateDataService;
import com.elstele.bill.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.io.IOException;

@Configuration
@EnableAsync
@EnableScheduling
public class Scheduler {
    @Autowired
    USDRateDataService usdRateDataService;

    final public static Logger LOGGER = LogManager.getLogger(Scheduler.class);

    @Scheduled(cron = Constants.EVERY_DAY_IN_10_30)
    public void getUSDRateXML(){
        usdRateDataService.getXMLUSDRate();
    }
    @Scheduled(cron=Constants.EVERY_WORK_DAY_IN_16_36)
    public void CalculateMonthTransactionReport(){
        LOGGER.info("scheduler month report");
        try {

            File file = new File("d:\\tanya_report_test.txt");

            if (file.createNewFile()){
                LOGGER.info("File is created!");
            }else{
                LOGGER.info("File already exists.");
            }

        } catch (IOException e) {
           LOGGER.error(e.getMessage(), e);
        }
    }


}

