package org.leon.dev.function.exceptions;

/**
 * Created by LeonWong on 15/6/25.
 */
public class ParamRequiredException extends Exception {
    public ParamRequiredException() {
        super();
    }

    public ParamRequiredException(String message) {
        super(message);
    }

    public ParamRequiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamRequiredException(Throwable cause) {
        super(cause);
    }

    protected ParamRequiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
