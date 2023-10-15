package objects.player;

import objects.Color;

public class Bot implements Player{
    private String name;
    private Color color;

	public Bot(Player enemy) {
		this.color = enemy.getColor().getOposite();
	}

    public void setName(String name) throws PlayerException {
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
	public String toString() {
		return "Player with the " + getColor().print() + " pieces";
	}
}
