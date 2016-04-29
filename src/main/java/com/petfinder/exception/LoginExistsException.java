package com.petfinder.exception;

public class LoginExistsException extends Exception {
	private final static String message = "Selected login already exists";
	
	public LoginExistsException(){
		super(message);
	}
	
	public LoginExistsException(String message) {
		super(message);
	}
}
