package com.joao.objects;

public class PlayerException extends Exception {
    private String message;

    public PlayerException(String message) {
		this.message = message;
    }
    
    @Override
    public String getMessage() {
		return message;
    }
}
