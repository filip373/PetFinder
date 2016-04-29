package com.petfinder.exception;

public class InvalidEmailException extends Exception {
	private final static String message = "Selected email is invalid.";
	
	public InvalidEmailException(){
		super(message);
	}
	
	public InvalidEmailException(String message) {
		super(message);
	}
}
