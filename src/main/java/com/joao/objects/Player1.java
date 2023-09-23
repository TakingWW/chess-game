package com.joao.objects;

import java.io.IOException;

public class Player1 implements Player {
    private Piece.COLOR color;
    private String name;

    public void setName(String name) throws PlayerException {
	if (name.contains(" ")) {
	    throw new PlayerException("Names with spaces are not acepted");
	}
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
	while (name == null) {
	    try {
		System.out.print("Take a nick name for youself: ");
		String name = System.console().readLine();
		setName(name);
	    } catch (PlayerException e) {
		e.printStackTrace();
	    }
	}

	try {
	    System.out.print("Choose your pieces color, W to white B to black: ");
 
	    char color = (char) System.in.read();
	    setColor(color);
	} catch(IOException e) {
	    e.printStackTrace();
	} catch (PlayerException e) {
	    System.out.println("Default color set to black");
	    this.color = Piece.COLOR.BLACK;
	}
    }

    @Override
    public boolean equals(Object o) {
	Player other = (Player) o;
	return other.getColor() == color;
    }
}
