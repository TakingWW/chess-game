package objects.piece;

import objects.Position;
import objects.Square;
import objects.Color;

import java.util.HashSet;
import java.util.List;

public interface Piece {

    public HashSet<Position> getMoves();
    public Color getColor();
    public int getMovements(int i);
    public String getName();
	public void updateMoves(Position currentPosition, List<Square> squares);

    public default String getScapeChar() {
		return "\u001B[0m";
	}
}
