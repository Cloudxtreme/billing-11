package com.elstele.bill.utils.Scheduler;

import com.elstele.bill.datasrv.interfaces.USDRateDataService;
import com.elstele.bill.utils.Constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Configuration
@EnableAsync
@EnableScheduling
public class Scheduler {
    @Autowired
    USDRateDataService usdRateDataService;

    @Scheduled(cron = Constants.EVERY_DAY_IN_10_30)
    public void getUSDRate(){
        usdRateDataService.setRate();
    }
}
