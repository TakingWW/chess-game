package com.joao.objects;

public class IlegalMoveException extends Exception {
    private String message = "You made an ilegal move";

    public IlegalMoveException(String message) {
	this.message = message;
    }

    public IlegalMoveException() {}
    
    @Override
    public String getMessage() {
	return message;
    }
}
