package com.elstele.bill.exceptions;

public class TariffFileIncorrectExtensionException extends Exception {
    public TariffFileIncorrectExtensionException() {
        super();
    }

    public TariffFileIncorrectExtensionException(String message) {
        super(message);
    }

    public TariffFileIncorrectExtensionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TariffFileIncorrectExtensionException(Throwable cause) {
        super(cause);
    }

    protected TariffFileIncorrectExtensionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
