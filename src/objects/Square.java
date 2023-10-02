package objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

import objects.piece.Piece;

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

	public boolean compareCoordinates(String coordinates) {
		Map<String, Integer> cols = Map.of("a", 1, "b", 2, "c", 3, "d", 4, "e", 5, "f", 6, "g", 7, "h", 8);
		int posX = cols.get(coordinates.substring(0, 1)) - 1;
		int posY = Integer.parseInt(coordinates.substring(1, 2)) - 1;
		return (new Position(posX, posY)).equals(position);
	}

    @Override
    public String toString() {
		return position.toString();
    }
}
