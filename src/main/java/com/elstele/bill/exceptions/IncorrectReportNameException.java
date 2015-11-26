package com.elstele.bill.exceptions;

public class IncorrectReportNameException extends Exception {
    public IncorrectReportNameException() {
    }

    public IncorrectReportNameException(String message) {
        super(message);
    }

    public IncorrectReportNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectReportNameException(Throwable cause) {
        super(cause);
    }

    public IncorrectReportNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
