package com.joao.objects;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
	this.x = x;
	this.y = y;
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
	return "[" + x + ", " + y + "]";
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
