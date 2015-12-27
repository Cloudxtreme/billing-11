package com.elstele.bill.utils;


public class  Constants {

    public static final String LOCAL_USER = "localUser";

    public enum AccountType { PRIVATE, LEGAL }

    public static final int SECOND = 1000;
    public static final int MINUTE = 60000;
    public static final int DAY = 86400000;
    public static final int HOUR = 3600000;
    public static final String EVERY_DAY_IN_10_30 = "0 30 10 * * *";

    public enum Period { WEEK, MONTH, YEAR }

    public enum TransactionSource { HANDMADE, BANK, BILLING }

    public enum TransactionDirection {DEBET, CREDIT }

    public static final String SERVICE_INTERNET = "INTERNET";
    public static final String SERVICE_PHONE = "PHONE";
    public static final String SERVICE_MARKER = "MARKER";
    public static final int TRANSACTION_DISPLAY_LIMIT = 20;

    public static final String BILLING_SERVICE_WORKER = "billingServiceWorker";
    public static final String BILLING_CALL_WORKER = "billingCallWorker";

}
