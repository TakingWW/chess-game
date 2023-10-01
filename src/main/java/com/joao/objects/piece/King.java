package com.joao.objects.piece;

import com.joao.objects.Color;
import com.joao.objects.Position;
import com.joao.objects.Square;

import java.util.HashSet;
import java.util.List;

public class King implements Piece {
	private Color color;
	private HashSet<Position> moves;
	public int moveCount;

	public King(Color color) {
		this.color = color;
	}
	
	public HashSet<Position> getMoves() {
		return moves;
	}
	
    public Color getColor() {
		return color;
	}
	
    public int getMovements(int i) {
		if (i > 0) {
			moveCount += i;
			return 0;
		}
		return moveCount;
	}
	
    public String getName() {
		return "k";
	}
	
    public void updateMoves(Position currentPosition, List<Square> squares) {
		HashSet<Position> positions = new HashSet<>();
		HashSet<Position> toRemove = new HashSet<>();
		HashSet<Position> validPositions = new HashSet<>();

		positions.add(currentPosition.sum(1, 0));
		positions.add(currentPosition.sum(1, 1));
		positions.add(currentPosition.sum(0, 1));
		positions.add(currentPosition.sum(-1, 0));
		positions.add(currentPosition.sum(-1, -1));
		positions.add(currentPosition.sum(0, -1));
		positions.add(currentPosition.sum(1, -1));
		positions.add(currentPosition.sum(-1, 1));

		if (moveCount == 0) {
			positions.add(currentPosition.sum(2, 0));
			positions.add(currentPosition.sum(-2, 0));
		}

		for (int i = 0; i < squares.size(); i++) {
			Square square = squares.get(i);
			Piece piece = square.getPiece();
			Position squarePosition = square.getPosition();

			validPositions.add(squarePosition);
			
			if (piece == null) continue;
			if (piece.getColor() == color) {
				toRemove.add(squarePosition);
			}
			
			Position abs = Position.abs(squarePosition.minus(currentPosition));
			for (int k = 1; k < 8; k++) {
				toRemove.add(squarePosition.sum(Position.multiply(k, abs)));
			}
		}


		for (Position position : positions) {
			if (!validPositions.contains(position)) {
				toRemove.add(position);
			}
		}

		positions.removeAll(toRemove);
		moves = positions;
    }

	@Override
	public String toString() {
		return color.toString() + "󰡗" + getScapeChar();
	}

	public static Position getInitialPosition(Color pieceColor) {
		switch (pieceColor) {
			case BLACK:
				return new Position(4, 7);
			case WHITE:
				return new Position(4, 0);
			default:
				return new Position(0, 0);
		}
	}
}
