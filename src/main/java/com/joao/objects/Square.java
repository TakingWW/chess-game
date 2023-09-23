package com.joao.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Square {
    // TODO colocar piece como optional
    private Piece piece;
    private Piece.COLOR color;
    private Position position;

    public Square(Piece piece, Piece.COLOR color, Position position) {
	this.piece = piece;
	this.color = color;
	this.position = position;
    }

    public Piece getPiece() {
	return piece;
    }

    public Piece.COLOR getColor() {
	return color;
    }

    public Position getPosition() {
	return position;
    }

    public void setPiece(Piece piece) {
	this.piece = piece;
    }

    @Override
    public String toString() {
	Map<Integer, String> cols = Map.of(1 , "a", 2,  "b", 3, "c", 4, "d", 5, "e", 6, "f", 7, "g", 8, "h");

	String col = cols.get(position.x + 1);
	String row = String.valueOf(position.y + 1);
	return "" + col + row;
    }
}
