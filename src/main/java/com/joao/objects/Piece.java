package com.joao.objects;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Piece {
    private Piece.TYPE type;
    private Piece.COLOR color;
    
    public static enum TYPE {
	PAWN,
	BISHOP,
	KNIGHT,
	ROOK,
	QUEEN,
	KING;

	@Override
	public String toString() {
		switch(this) {
		    case PAWN:
			return "󰡙";
		    case BISHOP:
			return "󰡜";
		    case KNIGHT:
			return "󰡘";
		    case ROOK:
			return "󰡛";
		    case QUEEN:
			return "󰡚";
		    case KING:
			return "󰡗";
		    default:
			return "";
		}
	    }

	public String getName() {
		switch(this) {
		    case PAWN:
			return "P";
		    case BISHOP:
			return "B";
		    case KNIGHT:
			return "N";
		    case ROOK:
			return "R";
		    case QUEEN:
			return "Q";
		    case KING:
			return "K";
		    default:
			return "";
		}
	    }
    }

    public static enum COLOR {
	BLACK,
	WHITE;

	@Override
	public String toString() {
		switch(this) {
		    case BLACK:
			return "\u001B[90m";
		    case WHITE:
			return "\u001B[37m";
		    default:
			return "";
		}
	    }
	public String print() {
		switch(this) {
		    case BLACK:
			return "black";
		    case WHITE:
			return "white";
		    default:
			return "";
		}
	    }
    }

    public Piece(TYPE type, COLOR color) {
	this.type = type;
	this.color = color;
    }
    public Piece(Piece piece) {
	this.type = piece.type;
	this.color = piece.color;
    }

    public HashSet<Position> moves(Position currentPosition, List<Square> squares) {
	HashSet<Position> positions = new HashSet<>();

	switch(type) {
	    case PAWN:
		if (color == Piece.COLOR.BLACK) {
		    positions.add(currentPosition.sum(0, -1));
		    if (currentPosition.y == 6) {
			positions.add(currentPosition.sum(0, -2));
		    }
		    positions.add(currentPosition.sum(1, -1));
		    positions.add(currentPosition.sum(-1, -1));
		} else {
    		    positions.add(currentPosition.sum(0, 1));
		    if (currentPosition.y == 1) {
			positions.add(currentPosition.sum(0, 2));
		    }
		    positions.add(currentPosition.sum(1, 1));
		    positions.add(currentPosition.sum(-1, 1));
		}
		break;
	    case KNIGHT:
		positions.add(currentPosition.sum(2, 1));
		positions.add(currentPosition.sum(2, -1));
		positions.add(currentPosition.sum(1, 2));
		positions.add(currentPosition.sum(1, -2));

		positions.add(currentPosition.sum(-2, -1));
		positions.add(currentPosition.sum(-2, 1));
		positions.add(currentPosition.sum(-1, -2));
		positions.add(currentPosition.sum(-1, 2));
		break;
	    case BISHOP:
		for (int i = 0; i < 8; i++) {
		    positions.add(currentPosition.sum(i, i));
		    positions.add(currentPosition.sum(-i, i));
		    positions.add(currentPosition.sum(i, -i));
		    positions.add(currentPosition.sum(-i, -i));
		}
		break;
	    case ROOK:
		for (int i = 0; i < 8; i++) {
		    positions.add(currentPosition.sum(0, i));
		    positions.add(currentPosition.sum(0, -i));
		    positions.add(currentPosition.sum(i, 0));
		    positions.add(currentPosition.sum(-i, 0));

		}
		break;
	    case QUEEN:
		for (int i = 0; i < 8; i++) {
		    positions.add(currentPosition.sum(0, i));
		    positions.add(currentPosition.sum(0, -i));
		    positions.add(currentPosition.sum(i, 0));
		    positions.add(currentPosition.sum(-i, 0));

		}
	    	for (int i = 0; i < 8; i++) {
		    positions.add(currentPosition.sum(i, i));
		    positions.add(currentPosition.sum(-i, i));
		    positions.add(currentPosition.sum(i, -i));
		    positions.add(currentPosition.sum(-i, -i));
		}
		break;
	    case KING:
		positions.add(currentPosition.sum(1, 1));
		positions.add(currentPosition.sum(1, -1));
		positions.add(currentPosition.sum(-1, 1));
		positions.add(currentPosition.sum(-1, -1));
		positions.add(currentPosition.sum(0, 1));
		positions.add(currentPosition.sum(1, 0));
		positions.add(currentPosition.sum(-1, 0));
		positions.add(currentPosition.sum(0, -1));
		break;
	    default:
		return null;
	}

	HashSet<Position> coordinates = new HashSet<>();
	HashSet<Position> toRemove = new HashSet<>();

	for (Square square : squares) {
	    Position position = square.getPosition();
	    coordinates.add(position);
	    if (square.getPiece() == null) {
		if (type == TYPE.PAWN & (position.x == currentPosition.x + 1 | position.x == currentPosition.x - 1)) {
		    toRemove.add(position);
		}
		continue;
	    }
	    Position abs = Position.abs(position.minus(currentPosition));
	    for (int i = 1; i < 8; i++) {
		toRemove.add(position.sum(Position.multiply(i, abs)));
	    }
	    if (square.getPiece().getColor() == color) {
		toRemove.add(position);
	    }
	}

	for (Position position : positions) {
	    if (!coordinates.contains(position)) {
		toRemove.add(position);
	    }
	}

	positions.removeAll(toRemove);
	
	return positions;
    }

    public Piece.COLOR getColor() {
	return color;
    }

    public Piece.TYPE getType() {
	return type;
    }

    public static String getScapeChar() {
	return "\u001B[0m";
    }
}
