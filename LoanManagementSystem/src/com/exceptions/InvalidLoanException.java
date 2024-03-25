package com.exceptions;

public class InvalidLoanException extends Exception {

	private static final long serialVersionUID = -4616570666140985923L;

	String message;

	public InvalidLoanException(String message) {
		super();
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
	
}
