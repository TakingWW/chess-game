package objects.player;

import java.io.IOException;

import static objects.Util.*;
import objects.Color;

public class Player1 implements Player {
    private Color color;
    private String name;

    public Player1(String color) {
		switch (color) {
			case "w":
				this.color = Color.WHITE;
			case "b":
				this.color = Color.BLACK;
			default:
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

    public String getName() {
		return name;
    }

    @Override
    public boolean equals(Object o) {
		Player other = (Player) o;
		return other.getColor() == color;
    }

	@Override
	public String toString() {
		return "Player with the " + getColor().print() + " pieces";
	}
}
