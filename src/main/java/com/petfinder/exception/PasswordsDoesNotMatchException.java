package com.petfinder.exception;

public class PasswordsDoesNotMatchException extends Exception {
    public PasswordsDoesNotMatchException() {
    }

    public PasswordsDoesNotMatchException(String message) {
        super(message);
    }

    public PasswordsDoesNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordsDoesNotMatchException(Throwable cause) {
        super(cause);
    }

    public PasswordsDoesNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
