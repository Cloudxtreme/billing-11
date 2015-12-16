package com.elstele.bill.executors;

import com.elstele.bill.datasrv.interfaces.AccountDataService;
import com.elstele.bill.datasrv.interfaces.TransactionDataService;

/**
 * Created by ivan on 15/12/16.
 */
public class BillingServiceWorker implements Worker, Runnable {

    private TransactionDataService tds;
    private AccountDataService ads;

    @Override
    public void run() {

        //get service cost

        //create transaction for bill

        //decrease account balance by service cost

    }
}
