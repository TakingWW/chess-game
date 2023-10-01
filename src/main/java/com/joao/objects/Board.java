package com.joao.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

import com.joao.objects.piece.Piece;
import com.joao.objects.piece.Pawn;
import com.joao.objects.piece.Bishop;
import com.joao.objects.piece.Rook;
import com.joao.objects.piece.Knight;
import com.joao.objects.piece.King;
import com.joao.objects.piece.Queen;

public class Board {
    private String map;
    private Player player;
    private Player toPlay;
    private Player bot = new Bot();
    public boolean gameOver = false;
    private Map<String, Integer> cols;
    private List<Square> squares = new ArrayList<>(64);
	private HashMap<Color, HashSet<Position>> attackedPositions = new HashMap<>();

    public Board(Player player) throws PlayerException {
		this.player = player;
		if (player.getColor() == Color.BLACK) {
			bot.setColor(Color.WHITE.print().charAt(0));
			toPlay = bot;
		} else {
			bot.setColor(Color.BLACK.print().charAt(0));
			toPlay = player;
		}

		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++) {
				Square square;
				Piece piece = null;
				if (j == 6) {
					piece = new Pawn(Color.BLACK);
				} else if ((j == 7 && i == 2) | (j == 7 && i == 5)) {
					piece = new Bishop(Color.BLACK);
				} else if ((j == 7 && i == 0) | (j == 7 && i == 7)) {
					piece = new Rook(Color.BLACK);
				} else if ((j == 7 && i == 1) | (j == 7 && i == 6)) {
					piece = new Knight(Color.BLACK);
				} else if (j == 7 && i == 3) {
					piece = new Queen(Color.BLACK);
				} else if (j == 7 && i == 4) {
					piece = new King(Color.BLACK);
				}
				if (j == 1) {
					piece = new Pawn(Color.WHITE);
				} else if ((j == 0 && i == 2) | (j == 0 && i == 5)) {
					piece = new Bishop(Color.WHITE);
				} else if ((j == 0 && i == 0) | (j == 0 && i == 7)) {
					piece = new Rook(Color.WHITE);
				} else if ((j == 0 && i == 1) | (j == 0 && i == 6)) {
					piece = new Knight(Color.WHITE);
				} else if (j == 0 && i == 3) {
					piece = new Queen(Color.WHITE);
				} else if (j == 0 && i == 4) {
					piece = new King(Color.WHITE);
				}
		
				if ((i + j) % 2 == 0) {
					square = new Square(piece, Color.BLACK, new Position(i, j));
				} else {
					square = new Square(piece, Color.WHITE, new Position(i, j));
				}
				attackedPositions.put(Color.BLACK, new HashSet<Position>());
				attackedPositions.put(Color.WHITE, new HashSet<Position>());
				squares.add(square);
			}

			cols = Map.of("a", 1 , "b", 2, "c", 3, "d", 4, "e", 5, "f", 6, "g", 7, "h", 8);
		}
	}

	public void draw() {
		map = "";
		map = map + " |---------------";
		int line = 0;
		for (int i = 0; i < squares.size(); i++) {
			if (i % 8 == 0) {
				map += "|\n";
				line = (8 - (i - (i % 8)) / 8);
				map = map + line + "|";
			} else {
				map += " ";
			}
			
			Piece piece = squares.get(squares.size() - i - 1).getPiece();

			if (piece == null) {
				map += " ";
			} else {
				map += piece.getColor() + piece.toString() + piece.getScapeChar();
			}
		}
	
		map = map + "|\n |---------------|";
		map = map + "\n  a b c d e f g h \n";

		System.out.println(map);
	}

	private void movePiece(Square from, Square to) {
		from.getPiece().getMovements(1);
		to.setPiece(from.getPiece());
		from.deletePiece();
	}

	private void changeToPlay() {
		if (toPlay == player) toPlay = bot;
		else toPlay = player;
	}

	public Player getToPlay() {
		return toPlay;
    }

	private void updateSquares() {
		for (Square square : squares) {
			Piece piece = square.getPiece();
			if (piece == null) continue;
			piece.updateMoves(square.getPosition(), squares);
			attackedPositions.get(piece.getColor()).addAll(piece.getMoves());
		}
	}

	public void playMove(String move) throws IllegalMoveException {
		boolean shortCastle = move.toLowerCase().equals("o-o");
		boolean longCastle = move.toLowerCase().equals("o-o-o");
		boolean castle = shortCastle | longCastle;

		if (move.length() != 3 & !castle) throw new IllegalMoveException("Ilegal move format");

		int xPos;
		int yPos;
		Square squareTo = null;
		Square kingSquareTo = null;
		Square kingSquareFrom = null;
		Position nextPosition = null;
		Position kingSquarePositionTo = null;
		List<Square> possibleMoves = new ArrayList<>();

		if (castle)	{
			move = "r";
			int castleIntIndentifier = shortCastle ? 1 : -1;
			nextPosition = King.getInitialPosition(toPlay.getColor()).sum(castleIntIndentifier, 0);
			kingSquarePositionTo = nextPosition.sum(castleIntIndentifier, 0);
		} else {
			try {
				xPos = cols.get(move.substring(1, 2)) - 1;
				yPos = Integer.parseInt(move.substring(2, 3)) - 1;
				nextPosition = new Position(xPos, yPos);
			} catch (NumberFormatException | NullPointerException e) {
				throw new IllegalMoveException("Move not Found");
			}
		}

		String pieceName = move.substring(0, 1);
		attackedPositions.get(Color.WHITE).clear();
		attackedPositions.get(Color.BLACK).clear();

		for (int i = 0; i < squares.size(); i++) {

			Square square = squares.get(i);
			Piece squarePiece = square.getPiece();
			Position squarePosition = square.getPosition();
			
			if (nextPosition.equals(squarePosition)) squareTo = square;
			if (squarePosition.equals(kingSquarePositionTo)) kingSquareTo = square;
			if (squarePiece == null) continue;

			squarePiece.updateMoves(squarePosition, squares);
			attackedPositions.get(squarePiece.getColor()).addAll(squarePiece.getMoves());
			
			if (squarePiece.getColor() != toPlay.getColor()) continue;
			if (squarePiece.getName().equals("k")) {
				kingSquareFrom = square;
				castle = squarePiece.getMoves().contains(kingSquarePositionTo) & castle;
				if (castle & shortCastle) {
					if (attackedPositions.get(squarePiece.getColor().getOposite()).contains(squarePosition.sum(1, 0))) {
						throw new IllegalMoveException("Cannot pass through an attacking square with king");
					}
				} else if (castle & shortCastle) {
					if (attackedPositions.get(squarePiece.getColor().getOposite()).contains(squarePosition.sum(-1, 0))) {
						throw new IllegalMoveException("Cannot pass through an attacking square with king");
					}
				}
			}
			
			if (!squarePiece.getName().equals(pieceName)) continue;
			if (!squarePiece.getMoves().contains(nextPosition)) continue;
			if (castle & squarePiece.getMovements(0) != 0) continue;

			possibleMoves.add(square);
		}

		int size = possibleMoves.size();
		int number = 0;

		if (size == 0 | squareTo == null) throw new IllegalMoveException("Move not found");
		if (size > 1) {
			while (number == 0) {
				try {
					System.out.println("More than one move found choose one from " + possibleMoves + " using number between 1 and " + size);
					number = Integer.parseInt(System.console().readLine()) - 1;
				} catch (NumberFormatException e) {
					System.out.println("This number is not valid");
				}
			}
		}

		Square squareFrom = possibleMoves.get(number);
		List<Square> movedSquares = new ArrayList<>(4);
		
		movePiece(squareFrom, squareTo);
		movedSquares.add(squareFrom);
		movedSquares.add(squareTo);
		if (kingSquareTo == null) kingSquareTo = kingSquareFrom;
		if (castle) {
			movePiece(kingSquareFrom, kingSquareTo);
			movedSquares.add(kingSquareFrom);
			movedSquares.add(kingSquareTo);
		}
		updateSquares();
		if (attackedPositions.get(toPlay.getColor().getOposite()).contains(kingSquareTo.getPosition())) {
			for (Square square : movedSquares) {
				square.revertPiece();
			}
			throw new IllegalMoveException("King would be captured this way");
		}
		changeToPlay();
	}
}
