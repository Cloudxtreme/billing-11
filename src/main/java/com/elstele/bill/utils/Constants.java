package com.elstele.bill.utils;


public class  Constants {

    public static final String LOCAL_USER = "localUser";

    public enum AccountType { PRIVATE, LEGAL }

    public static final int HALF_HOUR_IN_SEC = 1800;
    public static final String EVERY_DAY_IN_10_30 = "0 30 10 * * *";
    public static final String EVERY_DAY_IN_0_05 = "0 05 0 * * *";
    public static final String EVERY_WORK_DAY_IN_16_36 = "0 56 16 * * MON-FRI";
    public static final int STATISTIC_MONTHS_DISPLAY_DEFAULT = 3;

    public static final float PROGRESS_DONE = 100;

    public enum Period { WEEK, MONTH, YEAR }


    //TODO need to delete handmade and bank, after DB cleanup
    public enum TransactionSource { HANDMADE, BANK, BILLING, BANK_PIVD, BANK_USB, NONSTOP24, KASSA }

    public enum TransactionDirection {DEBET, CREDIT }

    public static final String SERVICE_INTERNET = "INTERNET";
    public static final String SERVICE_PHONE = "PHONE";
    public static final String SERVICE_MARKER = "MARKER";
    public static final int TRANSACTION_DISPLAY_LIMIT = 20;

    public static final String BILLING_SERVICE_WORKER = "billingServiceWorker";
    public static final String BILLING_CALL_WORKER = "billingCallWorker";

    public static final String SUCCESS_MESSAGE = "successMessage";
    public static final String MESSAGE = "message";
    public static final String ERROR_MESSAGE = "errorMessage";

    public static final String DEVICE_ADD_SUCCESS = "device.add.new";
    public static final String DEVICE_UPDATE_SUCCESS = "device.success.update";
    public static final String USER_SUCCESS_DELETE = "user.success.deleted";
    public static final String USER_ERROR_UNIQUE_NAME = "user.error.unique.name";
    public static final String SERVICE_ERROR_UNIQUE_NAME = "service.error.unique.name";
    public static final String USER_ROLE_SUCCESS_DELETE = "userrole.success.delete";
    public static final String USER_ROLE_SUCCESS_ADD = "userrole.success.add";
    public static final String USERROLE_ERROR_UNIQUE_NAME = "userrole.error.unique.name";

    public static final String PATH_TO_UPLOAD_FOLDER = "resources\\files";
    public static final String PATH_TO_CSV_FOLDER = "resources\\files\\csvFiles";
    public static final String PATH_TO_DOCX_FOLDER = "resources\\files\\docxFiles";

    public static final String VEGA_CSV = "vega_csv";
    public static final String UKR_NET_CSV = "ukrnet_csv";

    public static final String ONLY_ACTIVE = "active";
    public static final String ALL_TYPES = "all";

    public static final String DIRECTION_DELETE_SUCCESS = "direction.delete.success";
    public static final String DIRECTION_DELETE_ERROR = "direction.delete.error";
    public static final String ZONE_DELETED_SUCCESS = "tariff.delete.success";
    public static final String ZONE_DELETED_ERROR = "tariff.delete.error";
    public static final String RULE_DELETED_SUCCESS = "rule.delete.success";
    public static final String RULE_DELETED_ERROR = "rule.delete.error";

    public static final String KDF_FILE_TYPE = ".kdf";
    public static final String DOCX_FILE_TYPE = ".docx";
    public static final String SIMPLE_DATE_FORMAT = "dd.MM.yyyy";

    public static final String BIGGER = "BIGGER";
    public static final String SMALLER = "SMALLER";
}
