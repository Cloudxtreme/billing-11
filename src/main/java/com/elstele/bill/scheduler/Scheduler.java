package com.elstele.bill.scheduler;

import com.elstele.bill.datasrv.interfaces.USDRateDataService;
import com.elstele.bill.utils.Constants;
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

    @Scheduled(cron = Constants.EVERY_DAY_IN_10_30)
    public void getUSDRateXML(){
        usdRateDataService.getXMLUSDRate();
    }
    //TODO fix "0 56 16 * * MON-FRI" to verbal constant
    @Scheduled(cron="0 56 16 * * MON-FRI")
    public void CalculateMonthTransactionReport(){
        System.out.println("scheduler month report");
        try {

            File file = new File("d:\\tanya_report_test.txt");

            if (file.createNewFile()){
                System.out.println("File is created!");
            }else{
                System.out.println("File already exists.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

