package com.joao.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

				squares.add(square);
			}

			cols = Map.of("a", 1 , "b", 2, "c", 3, "d", 4, "e", 5, "f", 6, "g", 7, "h", 8);
		}
	}

	public void draw() {
		map = "";
		map = map + " |---------------";
		for (int i = 0; i < squares.size(); i++) {
			Piece piece = squares.get(i).getPiece();
			if (i % 8 == 0) {
				map += "|\n";
				map = map + ((i - (i % 8)) / 8 + 1) + "|";
			} else {
				map += " ";
			}

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
		from.setPiece(null);
	}

	private void changeToPlay() {
		if (toPlay == player) toPlay = bot;
		else toPlay = player;
	}

	public Player getToPlay() {
		return toPlay;
    }

	public void playMove(String move) throws IlegalMoveException {
		boolean shortCastle = move.toLowerCase().equals("o-o");
		boolean longCastle = move.toLowerCase().equals("o-o-o");
		boolean castle = shortCastle | longCastle;

		if (move.length() != 3 & !castle) throw new IlegalMoveException("Ilegal move format");

		int xPos;
		int yPos;
		Square squareTo = null;
		List<Square> possibleMoves = new ArrayList<>();

		try {
			xPos = cols.get(move.substring(1, 2)) - 1;
			yPos = Integer.parseInt(move.substring(2, 3)) - 1;
		} catch (NumberFormatException | NullPointerException e) {
			throw new IlegalMoveException("Move not Found");
		}
		
		Position nextPosition = new Position(xPos, yPos);

		for (int i = 0; i < squares.size(); i++) {
			Square square = squares.get(i);
			Piece squarePiece = square.getPiece();

			if (nextPosition.equals(square.getPosition())) squareTo = square;
			if (squarePiece == null) continue;
			
			squarePiece.updateMoves(square.getPosition(), squares);
			
			if (squarePiece.getColor() != toPlay.getColor()) continue;
			if (!squarePiece.getName().equals(move.substring(0, 1))) continue;
			if (!squarePiece.getMoves().contains(nextPosition)) continue;

			possibleMoves.add(square);
		}
		
		int size = possibleMoves.size();
		int number = 0;

		if (size == 0 | squareTo == null) {
			throw new IlegalMoveException("Move not found");
		} else if (size > 1) {
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
		movePiece(squareFrom, squareTo);
		changeToPlay();
	}
}
