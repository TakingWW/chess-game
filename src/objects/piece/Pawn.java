package objects.piece;

import java.util.HashSet;
import java.util.List;

import objects.Position;
import objects.Square;
import objects.Color;

public class Pawn implements Piece {
    private Color color;
    private HashSet<Position> moves;
	public int moveCount;

    public Pawn(Color color) {
		this.color = color;
    }

    public HashSet<Position> getMoves() {
		return moves;
    }

	public Color getColor() {
		return color;
	}

	public String getName() {
		return "p";
	}

	public int getMovements(int i) {
		if (i > 0) {
			moveCount += i;
			return 0;
		}
		return moveCount;
	}

    public void updateMoves(Position currentPosition, List<Square> squares) {
		HashSet<Position> positions = new HashSet<>();
		HashSet<Position> toRemove = new HashSet<>();
		HashSet<Position> validPositions = new HashSet<>();

		positions.add(currentPosition.sum(0, color.getUnit()));
		positions.add(currentPosition.sum(1, color.getUnit()));
		positions.add(currentPosition.sum(-1, color.getUnit()));
		if (moveCount == 0) {
			positions.add(currentPosition.sum(0, 2 * color.getUnit()));
		}

		for (int i = 0; i < squares.size(); i++) {
			Square square = squares.get(i);
			Piece piece = square.getPiece();
			Position squarePosition = square.getPosition();
			
			validPositions.add(squarePosition);
			
			if (piece == null) {
				if (squarePosition.x + 1 == currentPosition.x |
					squarePosition.x - 1 == currentPosition.x) {
					toRemove.add(squarePosition);
				}
			} else if (piece.getColor() == color) {
				toRemove.add(squarePosition);
			} else if (squarePosition.x == currentPosition.x) {
				toRemove.add(squarePosition);
			}
			if (piece == null) continue;

			Position abs = Position.abs(squarePosition.minus(currentPosition));
			for (int k = 1; k < 8; k++) {
				toRemove.add(squarePosition.sum(Position.multiply(k, abs)));
			}
		}

		for (Position position : positions) {
			if (!validPositions.contains(position)) toRemove.add(position);
		}
		
		positions.removeAll(toRemove);
		moves = positions;
    }

	@Override
	public String toString() {
		return color.toString() + "󰡙" + getScapeChar();
	}
}
