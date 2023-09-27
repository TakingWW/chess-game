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
    private List<Piece.TYPE> piecesName;
    private Map<String, Integer> cols;

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
		
	piecesName = new ArrayList<>(Arrays.asList(
	    Piece.TYPE.PAWN,
	    Piece.TYPE.KNIGHT,
	    Piece.TYPE.BISHOP,
	    Piece.TYPE.ROOK,
	    Piece.TYPE.QUEEN,
	    Piece.TYPE.KING
	));

	cols = Map.of("a", 1 , "b", 2, "c", 3, "d", 4, "e", 5, "f", 6, "g", 7, "h", 8);
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
	if (move.length() != 3) throw new IlegalMoveException("Move wrote in wrong format");
	if (!toPlay.equals(player)) throw new IlegalMoveException("Not your turn");
	if (cols.get(move.substring(1, 2)) == null) throw new IlegalMoveException("Square not found");
	if (!cols.containsValue(Integer.parseInt(move.substring(2, 3)))) throw new IlegalMoveException("Square not found");
	
	int xPos;
	int yPos;

	try {
	    xPos = cols.get(move.substring(1, 2)) - 1;
	    yPos = Integer.parseInt(move.substring(2, 3)) - 1;
	} catch (NullPointerException e) {
	    throw new IlegalMoveException("Square not found");
	}

	Position nextPosition = new Position(xPos, yPos);
	System.out.println(nextPosition);
	Square squareTo = null;
	Piece.TYPE piece = null;
	
	for (Square square : squares) {
	    if (square.getPosition().equals(nextPosition)){
		squareTo = square;
	    }
	}

	for (Piece.TYPE type : piecesName) {
	    if (move.substring(0, 1).equals(type.getName().toLowerCase())) {
		piece = type;
	    }
	}
	if (squareTo == null) throw new IlegalMoveException("Caguei");
	if (piece == null) throw new IlegalMoveException("Caguei");

	Square squareFrom = calculateSquare(piece, squareTo);
	
	movePiece(squareFrom, squareTo);
	changeToPlay();

    }

    private Square calculateSquare(Piece.TYPE type, Square squareTo) throws IlegalMoveException {
	Position nextPosition = squareTo.getPosition();
	Square SquareFrom;
	ArrayList<Square> squaresFrom = new ArrayList<>();

	for (Square square : squares) {
	    Piece piece = square.getPiece();
	    
	    if (piece == null) continue;
	    if (piece.getType() != type) continue;
	    if (piece.getColor() != toPlay.getColor()) continue;

	    HashSet<Position> positions = piece.moves(square.getPosition(), squares);
	    if (positions.contains(nextPosition)) squaresFrom.add(square);
	}
	int number = 0;
	int size = squaresFrom.size();
	if (size == 0) throw new IlegalMoveException("Move not found");
	if (size > 1) {
	    System.out.println("More than one move found");
	    while (number == 0) {
		try {
		    System.out.print("Choose one square to move from: " + 1 + " to " + size + " in " + squaresFrom);
		    number = Integer.parseInt(System.console().readLine()) - 1;

		} catch (NumberFormatException | NullPointerException e) {
		    throw new IlegalMoveException("You don't have this choice");
		}
	    }
	}
	
	return squaresFrom.get(number);
    }

    private void movePiece(Square from, Square to) {
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
}
