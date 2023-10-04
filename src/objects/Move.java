package objects;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Collections;

import objects.piece.King;
import objects.piece.Piece;
import objects.player.Player;
import objects.command.Command;
import static objects.Util.*;

public class Move extends Command {
	static class Pair {
		public Square squareTo;
		public Square squareFrom;

		public boolean pairComplete() {
			if (squareFrom == null | squareTo == null) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	public final List<Pair> moves = new ArrayList<>();
	private final Board board;
	public boolean castle = false;

	public Move(String moveString, Board board) throws IllegalMoveException {
		moves.add(new Pair());

		this.board = board;
		List<Square> squares = board.getSquares();
		Player player = board.getToPlay();
		
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
		
		for (Square square : squares) {
			Piece piece = square.getPiece();

			if (square.getPosition().equals(nextPosition)) squareTo = square;
			
			if (piece == null) continue;
			if (piece.getColor() != player.getColor()) continue;
			if (!piece.getName().equals(moveString.substring(0, 1))) continue;
			if (!piece.getMoves().contains(nextPosition)) continue;

			moves.get(moves.size() - 1).squareFrom = square;
			moves.add(new Pair());
		}

		moves.remove(moves.size() - 1);
		
		String squareMessage = "";
		for (Pair pair : moves) {
			pair.squareTo = squareTo;
			
			if (!pair.pairComplete()) {
				pair = null;
				continue;
			} else {
				squareMessage += pair.squareFrom.toString() + " ";
			}
		}

		moves.removeAll(Collections.singleton(null));

		if (moves.size() > 1){
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
			
			if (square.getPosition().equals(nextPosition)) moves.get(ROOK_INDEX).squareTo = square;
			if (square.getPosition().equals(nextKingPosition)) moves.get(KING_INDEX).squareTo = square;
			
			if (piece == null) continue;
			if (piece.getColor() != player.getColor()) continue;
			if (piece.getName().equals("k") &
				piece.getMoves().contains(nextKingPosition) &
				!board.getAttackingSquares(player.getColor().
					getOposite()).contains(nextPosition)) moves.get(KING_INDEX).squareFrom = square;
			if (!piece.getName().equals("r")) continue;
			if (!piece.getMoves().contains(nextPosition)) continue;
			if (piece.getMovements(0) != 0) continue;
			
			moves.get(ROOK_INDEX).squareFrom = square;
		}

		for (Pair pair : moves) {
			if (!pair.pairComplete()) {
				moves.clear();
				break;
			}
		}
	}
	
	public void execute() throws IllegalMoveException {
		for (Pair pair : moves) {
			Square squareFrom = pair.squareFrom;
			Square squareTo = pair.squareTo;
			
			squareFrom.getPiece().getMovements(1);
			squareTo.setPiece(squareFrom.getPiece());
			squareFrom.deletePiece();
		}
		if (moves.isEmpty()) throw new IllegalMoveException("No move found");
		if (moves.size() > 0) {
			board.updateSquares();
			board.changeToPlay();
		}
	}

	public int chooseSquareFromList(String squaresMessage) {
		Optional<Integer> number = Optional.empty();
		if (!board.testArguments.isEmpty()) {
			number = Optional.of(board.testArguments.get(0));
			board.testArguments.remove(0);
		}
		
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
