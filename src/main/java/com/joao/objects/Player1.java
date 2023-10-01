package com.joao.objects;

import java.io.IOException;

public class Player1 implements Player {
    private Color color;
    private String name;
    private String flag = "no";

    public Player1(String otherPlayerFlag, char color) {
		if (!flag.equals(otherPlayerFlag.toLowerCase())) flag = "yes";

		try {
			setColor(color);
		} catch (PlayerException e) {
			System.out.println("Default color set to black");
			this.color = Color.BLACK;
		}
    }

    public void setName(String name) throws PlayerException {
		if (name.contains(" ")) {
			throw new PlayerException("Names with spaces are not acepted");
		}
		this.name = name;
    }

    public Color getColor() {
		return color;
    }

    public void setColor(char color) throws PlayerException {
		if (color == 'w' | color == 'W') {
			this.color = Color.WHITE;
		} else if (color == 'b' | color == 'B') {
			this.color = Color.BLACK;
		} else {
			throw new PlayerException("Color not found");
		}
    }

    public String getName() {
		return name;
    }

    @Override
    public boolean equals(Object o) {
		Player other = (Player) o;
		return other.getColor() == color;
    }
}
