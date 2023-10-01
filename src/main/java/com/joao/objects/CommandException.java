package com.joao.objects;

public class CommandException extends Exception {
	private String message;

	public CommandException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
