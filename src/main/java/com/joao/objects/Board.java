package com.joao.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Board {
    public boolean gameOver = false;
    private Player player;
    private Player bot = new Bot();
    private Player toPlay;
    private List<Square> squares = new ArrayList<>(64);
    private String map;

    public Board(Player player) throws PlayerException {
	this.player = player;
	if (player.getColor() == Piece.COLOR.BLACK) {
	    bot.setColor(Piece.COLOR.WHITE.print().charAt(0));
	    toPlay = bot;
	} else {
	    bot.setColor(Piece.COLOR.BLACK.print().charAt(0));
	    toPlay = player;
	}
	for (int j = 0; j < 8; j++) {
	    for (int i = 0; i < 8; i++) {
		Square square;
		Piece piece = null;
		if (j == 6) {
		    piece = new Piece(Piece.TYPE.PAWN, Piece.COLOR.BLACK);
		} else if ((j == 7 && i == 0) | (j == 7 && i == 7)) {
		    piece = new Piece(Piece.TYPE.ROOK, Piece.COLOR.BLACK);
		} else if ((j == 7 && i == 1) | (j == 7 && i == 6)) {
		    piece = new Piece(Piece.TYPE.KNIGHT, Piece.COLOR.BLACK);
		} else if ((j == 7 && i == 2) | (j == 7 && i == 5)) {
		    piece = new Piece(Piece.TYPE.BISHOP, Piece.COLOR.BLACK);
		} else if (j == 7 && i == 3) {
		    piece = new Piece(Piece.TYPE.QUEEN, Piece.COLOR.BLACK);
		} else if (j == 7 && i == 4) {
		    piece = new Piece(Piece.TYPE.KING, Piece.COLOR.BLACK);
		}		    
		if (j == 1) {
		    piece = new Piece(Piece.TYPE.PAWN, Piece.COLOR.WHITE);
		} else if ((j == 0 && i == 0) | (j == 0 && i == 7)) {
		    piece = new Piece(Piece.TYPE.ROOK, Piece.COLOR.WHITE);
		} else if ((j == 0 && i == 1) | (j == 0 && i == 6)) {
		    piece = new Piece(Piece.TYPE.KNIGHT, Piece.COLOR.WHITE);
		} else if ((j == 0 && i == 2) | (j == 0 && i == 5)) {
		    piece = new Piece(Piece.TYPE.BISHOP, Piece.COLOR.WHITE);
		} else if (j == 0 && i == 3) {
		    piece = new Piece(Piece.TYPE.QUEEN, Piece.COLOR.WHITE);
		} else if (j == 0 && i == 4) {
		    piece = new Piece(Piece.TYPE.KING, Piece.COLOR.WHITE);
		}		    
		
		if ((i + j) % 2 == 0) {
		    square = new Square(piece, Piece.COLOR.BLACK, new Position(i, j));
		} else {
		    square = new Square(piece, Piece.COLOR.WHITE, new Position(i, j));
		}

		squares.add(square);
	    }
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
		map += piece.getColor() + piece.getType().toString() + Piece.getScapeChar();
	    }
	}
	
	map = map + "|\n |---------------|";
	map = map + "\n  a b c d e f g h \n";

	System.out.println(map);
    }

    public void playMove(String move) throws IlegalMoveException {
	if (move.length() != 3) {
	    throw new IlegalMoveException("Move wrote in wrong format");
	}
	if (!toPlay.equals(player)) {
	    throw new IlegalMoveException("Wait for your turn");
	}
	
	ArrayList<String> piecesChar = new ArrayList<>(Arrays.asList(
	    Piece.TYPE.PAWN.getName(),
	    Piece.TYPE.KNIGHT.getName(),
	    Piece.TYPE.BISHOP.getName(),
	    Piece.TYPE.ROOK.getName(),
	    Piece.TYPE.QUEEN.getName(),
	    Piece.TYPE.KING.getName()
	));

	Map<String, Integer> cols = Map.of("a", 1 , "b", 2, "c", 3, "d", 4, "e", 5, "f", 6, "g", 7, "h", 8);
	ArrayList<String> rows = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8"));

	if (!piecesChar.contains(String.valueOf(move.charAt(0)).toUpperCase())) {
	    throw new IlegalMoveException("This piece does not exist");
	}
	if (!rows.contains(move.substring(2, 3))) {
	    throw new IlegalMoveException("This square does not exist");
	}

	int i;
	int j;
	
	try {
	    i = cols.get(String.valueOf(move.charAt(1)));
	} catch (NullPointerException e) {
	    throw new IlegalMoveException("This square does not exist");
	}
	try {
	    j = Integer.parseInt(move.substring(2,3));

	    Position nextPosition = new Position(i - 1, j - 1);
	    List<Square> possibleSquaresToMovePiece = new ArrayList<>();

	    for (Square square : squares) {
		Piece piece = square.getPiece();
		if (piece == null) continue;
		if (piece.getColor() != toPlay.getColor()) continue;
		if (!piece.getType().getName().equals(String.valueOf(move.charAt(0)).toUpperCase())) continue;

		HashSet<Position> pos = piece.moves(square.getPosition(), squares);
		if (pos.contains(nextPosition)) {
		    possibleSquaresToMovePiece.add(square);
		}
	    }
	    int size = possibleSquaresToMovePiece.size();
	    int number = 0;
	    if (size == 0) throw new IlegalMoveException("Ilegal move");
	    if (size > 1) {
		System.out.println("choose the square of the piece to move");
		int k = 1;
		for (Square square : possibleSquaresToMovePiece) {
		    System.out.print("(" + k + ") " + square + " ");
		    k += 1;
		}
		System.out.print(": ");
		number = Integer.parseInt(System.console().readLine()) - 1;
	    }
	    for (Square square : squares) {
		if (square.getPosition().equals(nextPosition)) {
		    square.setPiece(possibleSquaresToMovePiece.get(number).getPiece());
		    possibleSquaresToMovePiece.get(number).setPiece(null);
		}
	    }
	} catch (NullPointerException | NumberFormatException e) {
	    throw new IlegalMoveException();
	}
	if (toPlay.equals(player)) {
	    toPlay = bot;
	} else {
	    toPlay = player;
	}
    }

    public Player getToPlay() {
	return toPlay;
    }
}
