package com.petfinder.exception;

public class UserStateNotChangedException extends Exception {
    public UserStateNotChangedException() {
        super();
    }

    public UserStateNotChangedException(String message) {
        super(message);
    }

    public UserStateNotChangedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserStateNotChangedException(Throwable cause) {
        super(cause);
    }

    protected UserStateNotChangedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
