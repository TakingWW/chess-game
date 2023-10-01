package com.joao.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

import com.joao.objects.piece.Piece;

public class Square {
    private Piece piece;
    private Color color;
    private Position position;
	private List<Boolean> underAtack = new ArrayList<>(2);
	private Piece exPiece = null;

    public Square(Piece piece, Color color, Position position) {
		this.piece = piece;
		this.color = color;
		this.position = position;
    }

    public Piece getPiece() {
		return piece;
    }

    public Color getColor() {
		return color;
    }

    public Position getPosition() {
		return position;
    }

    public void setPiece(Piece piece) {
		exPiece = this.piece;
		this.piece = piece;
    }

	public void deletePiece() {
		exPiece = piece;
		piece = null;
	}

	public void revertPiece() {
		if (exPiece == null) {
			exPiece = piece;
			piece = null;
		} else {
			piece = exPiece;
			exPiece = null;
		}
	}

    @Override
    public String toString() {
		Map<Integer, String> cols = Map.of(1 , "a", 2,  "b", 3, "c", 4, "d", 5, "e", 6, "f", 7, "g", 8, "h");

		String col = cols.get(position.x + 1);
		String row = String.valueOf(position.y + 1);
		return "" + col + row;
    }
}
