package com.joao.objects;

public class IllegalMoveException extends Exception {
    private String message = "You made an ilegal move";

    public IllegalMoveException(String message) {
		this.message = message;
    }

    public IllegalMoveException() {}
    
    @Override
    public String getMessage() {
		return message;
    }
}
