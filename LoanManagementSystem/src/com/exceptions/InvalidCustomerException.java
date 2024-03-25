package com.exceptions;

public class InvalidCustomerException extends Exception {

	
	private static final long serialVersionUID = -5460388760586049740L;
	String message;

	public InvalidCustomerException(String message) {
		super();
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
	
}
