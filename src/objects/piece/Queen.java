package objects.piece;

import objects.Color;
import objects.Position;
import objects.Square;

import java.util.HashSet;
import java.util.List;

public class Queen implements Piece {
	private Color color;
	private HashSet<Position> moves;
	public int moveCount;

	public Queen(Color color) {
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
		return "q";
	}

    public void updateMoves(Position currentPosition, List<Square> squares) {
		HashSet<Position> positions = new HashSet<>();
		HashSet<Position> toRemove = new HashSet<>();
		HashSet<Position> validPositions = new HashSet<>();

		for (int i = 1; i < 8; i++) {
			positions.add(currentPosition.sum(i, i));
			positions.add(currentPosition.sum(i, -i));
			positions.add(currentPosition.sum(-i, -i));
			positions.add(currentPosition.sum(-i, i));

			positions.add(currentPosition.sum(i, 0));
			positions.add(currentPosition.sum(0, i));
			positions.add(currentPosition.sum(-i, 0));
			positions.add(currentPosition.sum(0, -i));
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
				toRemove.add(squarePosition.sum(Position.multiply(k ,abs)));
			}
		}

		for (Position position : positions) {
			if (!validPositions.contains(position))	toRemove.add(position);
		}

		positions.removeAll(toRemove);
		moves = positions;
    }

	@Override
	public String toString() {
		return color.toString() + "󰡚" + getScapeChar();
	}
}
