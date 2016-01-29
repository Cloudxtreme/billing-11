package com.elstele.bill.exceptions;

public class IncorrectCSVFileTyleException extends Exception {
    public IncorrectCSVFileTyleException() {
    }

    public IncorrectCSVFileTyleException(String message) {
        super(message);
    }

    public IncorrectCSVFileTyleException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectCSVFileTyleException(Throwable cause) {
        super(cause);
    }

    public IncorrectCSVFileTyleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
