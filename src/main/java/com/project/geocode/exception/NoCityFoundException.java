package com.project.geocode.exception;

public class NoCityFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	// Parameterless Constructor
	public NoCityFoundException() {
	}

	// Constructor that accepts a message
	public NoCityFoundException(String message) {
		super(message);
	}

}
