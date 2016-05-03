package com.petfinder.exception;

public class EmailExistsException extends Exception {
	private final static String message = "Selected email already exists";
	
	public EmailExistsException(){
		super(message);
	}
	
	public EmailExistsException(String message) {
		super(message);
	}
}
