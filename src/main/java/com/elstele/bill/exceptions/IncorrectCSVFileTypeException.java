package com.elstele.bill.exceptions;

public class IncorrectCSVFileTypeException extends Exception {
    public IncorrectCSVFileTypeException() {
    }

    public IncorrectCSVFileTypeException(String message) {
        super(message);
    }

    public IncorrectCSVFileTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectCSVFileTypeException(Throwable cause) {
        super(cause);
    }

    public IncorrectCSVFileTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
