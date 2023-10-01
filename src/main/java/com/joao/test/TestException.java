package com.joao.test;

public class TestException extends Exception {
	private String message;
	
	public TestException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
