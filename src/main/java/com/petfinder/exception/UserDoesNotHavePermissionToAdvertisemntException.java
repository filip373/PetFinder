package com.petfinder.exception;

public class UserDoesNotHavePermissionToAdvertisemntException extends Exception {
    public UserDoesNotHavePermissionToAdvertisemntException() {
    }

    public UserDoesNotHavePermissionToAdvertisemntException(String message) {
        super(message);
    }

    public UserDoesNotHavePermissionToAdvertisemntException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDoesNotHavePermissionToAdvertisemntException(Throwable cause) {
        super(cause);
    }

    public UserDoesNotHavePermissionToAdvertisemntException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
