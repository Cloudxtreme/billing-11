package com.elstele.bill.exceptions;


public class DirectionCallException extends Exception {
    public DirectionCallException(){};
    public DirectionCallException(String message) {
        super(message);
    }
    public DirectionCallException(Throwable cause) {
        super(cause);
    }

    public DirectionCallException(String message, Throwable cause) {
        super(message, cause);
    }

}
