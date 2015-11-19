package com.elstele.bill.scheduler;

import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.io.IOException;

public class TransactionScheduler {

    @Scheduled(cron="0 56 16 * * MON-FRI")
    public void CalculateMonthTransactionReport(){
        System.out.println("Scheduler month report");
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
