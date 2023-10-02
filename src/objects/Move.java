package objects;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import objects.piece.King;
import objects.piece.Piece;
import objects.player.Player;
import objects.command.Command;
import static objects.Util.*;

public class Move extends Command {
	class Pair {
		public Optional<Square> squareTo;
		public Optional<Square> squareFrom;

		public Pair() {
			squareTo = Optional.empty();
			squareFrom = Optional.empty();
		}
	}
	
	public List<Pair> moves = new ArrayList<>();
	public boolean castle = false;

	public Move(String moveString, List<Square> squares, Player player) throws IllegalMoveException {
		moves.add(new Pair());
		
		if (moveString.equals("o-o") | moveString.equals("o-o-o")) rock(moveString, squares, player);
		else commun(moveString, squares, player);
	}

	private void commun(String moveString, List<Square> squares, Player player) throws IllegalMoveException {
		if (moveString.length() != 3) throw new IllegalMoveException("Move in illegal format");

		Position nextPosition;
		Square squareTo = null;

		try {
			nextPosition = new Position(moveString.substring(1, 3));
		} catch (NullPointerException e) {
			throw new IllegalMoveException("Square not found");
		}
		
		for (int i = 0; i < squares.size(); i++) {
			Square square = squares.get(i);
			Piece piece = square.getPiece();

			if (square.getPosition().equals(nextPosition)) squareTo = square;
			
			if (piece == null) continue;
			if (piece.getColor() != player.getColor()) continue;
			if (!piece.getMoves().contains(nextPosition)) continue;

			moves.get(moves.size() - 1).squareFrom = Optional.of(square);
			moves.add(new Pair());
		}

		moves.remove(moves.size() - 1);
		
		if (moves.size() > 1){
			String squareMessage = "";
		
			for (int i = 0; i < moves.size(); i++) {
				moves.get(i).squareTo = Optional.of(squareTo);
				squareMessage += moves.get(i).squareFrom.get().toString() + " ";
			}
			
			int number = chooseSquareFromList(squareMessage);
			Pair move = moves.get(number);
			
			moves.clear();
			moves.add(move);
		}
	}

	private void rock(String moveString, List<Square> squares, Player player) throws IllegalMoveException {
		boolean shortCastle = moveString.equals("o-o");
		boolean longCastle = moveString.equals("o-o-o");
		int castleIndentifier = shortCastle ? 1 : -1;
		
		castle = shortCastle | longCastle;
		if (!castle) throw new IllegalMoveException("Move not valid");

		int KING_INDEX = 0;
		int ROOK_INDEX = 1;
		Position nextPosition = King.getInitialPosition(player.getColor()).sum(castleIndentifier, 0);
		Position nextKingPosition = nextPosition.sum(castleIndentifier, 0);
		
		moves.add(new Pair());

		for (int i = 0; i< squares.size(); i++) {
			Square square = squares.get(i);
			Piece piece = square.getPiece();
			
			if (square.getPosition().equals(nextPosition)) moves.get(ROOK_INDEX).squareTo = Optional.of(square);
			if (square.getPosition().equals(nextKingPosition)) moves.get(KING_INDEX).squareTo = Optional.of(square);
			
			if (piece == null) continue;
			if (piece.getColor() != player.getColor()) continue;
			if (piece.getName().equals("k")) moves.get(KING_INDEX).squareFrom = Optional.of(square);
			if (!piece.getName().equals("r")) continue;
			if (!piece.getMoves().contains(nextPosition)) continue;
			if (piece.getMovements(0) != 0) continue;

			moves.get(ROOK_INDEX).squareFrom = Optional.of(square);
		}

		if (!moves.get(KING_INDEX).squareFrom.get().getPiece().getMoves().contains(nextKingPosition)) {
			moves.get(KING_INDEX).squareTo = Optional.empty();
			moves.get(ROOK_INDEX).squareTo = Optional.empty();
		}
	}
	
	public void execute() {
		for (Pair pair : moves) {
			if (pair.squareFrom.isPresent() & pair.squareTo.isPresent()) {
				Square squareFrom = pair.squareFrom.get();
				Square squareTo = pair.squareTo.get();
			
				squareFrom.getPiece().getMovements(1);
				squareTo.setPiece(squareFrom.getPiece());
				squareFrom.deletePiece();
			}
		}
	}

	public int chooseSquareFromList(String squaresMessage) {
		Optional<Integer> number = Optional.empty();
		
		while (!number.isPresent()) {
			print("More than one move found choose one from " + squaresMessage + "using number between 1 and " + moves.size());
			Integer in = Integer.parseInt(System.console().readLine());
			if (in > moves.size()) continue;
			else if (in < 0) continue;
			number = Optional.of(Integer.valueOf(in - 1));
		}
		
		return number.get().intValue();
	}
}
