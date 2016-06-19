package com.petfinder.exception;

public class UserDoesNotHavePermissionToAdvertisementException extends Exception {
    public UserDoesNotHavePermissionToAdvertisementException() {
    }

    public UserDoesNotHavePermissionToAdvertisementException(String message) {
        super(message);
    }

    public UserDoesNotHavePermissionToAdvertisementException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDoesNotHavePermissionToAdvertisementException(Throwable cause) {
        super(cause);
    }

    public UserDoesNotHavePermissionToAdvertisementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
