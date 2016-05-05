package com.petfinder.exception;

public class LoginExistsException extends Exception {

	public LoginExistsException(){
		super();
	}
	
	public LoginExistsException(String message) {
		super(message);
	}

	public LoginExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoginExistsException(Throwable cause) {
		super(cause);
	}

	public LoginExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
