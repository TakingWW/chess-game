package objects;

import java.util.Map;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
		this.x = x;
		this.y = y;
    }

	public Position(String coordinates) {
		int posX;
		int posY;
		String col = coordinates.substring(1, 2);
		String row = coordinates.substring(2, 3);
		Map<String, Integer> cols = Map.of("a", 1 , "b", 2, "c", 3, "d", 4, "e", 5, "f", 6, "g", 7, "h", 8);

		try {
			posX = cols.get(col) - 1;
			posY = Integer.parseInt(row) - 1;
			if (posY > 7 | posY < 0) throw new NullPointerException();
		} catch (NumberFormatException | NullPointerException e) {
			throw new NullPointerException();
		}

		this.x = posX;
		this.y = posY;
	}

    public Position sum(int i, int j) {
		int posX = i + x;
		int posY = j + y;
		return new Position(posX, posY);
    }

    public Position sum(Position pos) {
		int posX = pos.x + x;
		int posY = pos.y + y;
		return new Position(posX, posY);
    }

    public static Position multiply(int c, Position pos) {
		int x = pos.x * c;
		int y = pos.y * c;
		return new Position(x, y);
    }

    public Position minus(Position pos) {
		int posX = x - pos.x;
		int posY = y - pos.y;
		return new Position(posX, posY);
    }

    public static Position abs(Position pos) {
		int x = 0;
		int y = 0;
		try {x = pos.x / Math.abs(pos.x);} catch (ArithmeticException e) {}
		try {y = pos.y / Math.abs(pos.y);} catch (ArithmeticException e) {}
		return new Position(x, y);
    }

    @Override
    public String toString() {
		Map<Integer, String> cols = Map.of(0, "a", 1 , "b", 2, "c", 3, "d", 4, "e", 5, "f", 6, "g", 7, "h");
		String posX = cols.get(this.x);
		return "[" + posX + (y + 1) + "]";
    }

    @Override
    public int hashCode() {
		return x * 100 + y;
    }

    @Override
    public boolean equals(Object o) {
		if (o instanceof Position) {
			Position pos = (Position) o;
			return pos.x == x & pos.y == y;
		}
		return false;
    }
}
