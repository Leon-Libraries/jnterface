package org.leon.dev.function.exceptions;


/**
 * Created by LeonWong on 15/6/25.
 */
public class FunctionNotFoundException extends Exception {

    public FunctionNotFoundException() {
        super();
    }

    public FunctionNotFoundException(String message) {
        super(message);
    }

    public FunctionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FunctionNotFoundException(Throwable cause) {
        super(cause);
    }

    protected FunctionNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
