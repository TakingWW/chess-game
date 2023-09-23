package com.joao.objects;

public class Bot implements Player{
    private String name;
    private Piece.COLOR color;
    
    public void setName(String name) throws PlayerException {
	this.name = name;
    }

    public Piece.COLOR getColor() {
	return color;
    }
    
    public void setColor(char color) throws PlayerException {
	if (color == 'w' | color == 'W') {
	    this.color = Piece.COLOR.WHITE;
	} else if (color == 'b' | color == 'B') {
	    this.color = Piece.COLOR.BLACK;
	} else {
	    throw new PlayerException("Color not found");
	}
    }

    public String getName() {
	return name;
    }

    public void initialize() {
	name = "bot";
    }
}
