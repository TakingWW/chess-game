package objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

import objects.player.Bot;
import objects.player.Player;
import objects.player.PlayerException;
import objects.piece.Piece;
import objects.piece.Pawn;
import objects.piece.Bishop;
import objects.piece.Rook;
import objects.piece.Knight;
import objects.piece.King;
import objects.piece.Queen;
import objects.command.CommandException;
import objects.command.Command;
import static objects.Util.*;

public class Board {
    private Player player;
    private Player toPlay;
    private Player bot = new Bot();
    private List<Square> squares = new ArrayList<>(64);
	private HashMap<Color, HashSet<Position>> attackedPositions = new HashMap<>();
	public ArrayList<Integer> testArguments = new ArrayList<>();

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

			updateSquares();

		}
	}

	public void draw() {
		String map;
		map = " |---------------";
		int decreasingLine = 8;
		for (int i = 0; i < squares.size(); i++) {
			if (i % 8 == 0) {
				map += "|\n";
				int line = (8 - (i - (i % 8)) / 8);
				decreasingLine--;
				map = map + line + "|";
			} else {
				map += " ";
			}
			
			Piece piece = squares.get(decreasingLine * 8 + (i % 8)).getPiece();

			if (piece == null) {
				map += " ";
			} else {
				map += piece.getColor() + piece.toString() + piece.getScapeChar();
			}
		}
	
		map = map + "|\n |---------------|";
		map = map + "\n  a b c d e f g h \n";

		print(map);
	}

	public void changeToPlay() {
		if (toPlay == player) toPlay = bot;
		else toPlay = player;
	}

	public Player getToPlay() {
		return toPlay;
    }
	public Player getNotToPlay() {
		if (toPlay.equals(player)) return bot;
		else return player;
	}

	public List<Square> getSquares() {
		return squares;
	}

	public HashSet<Position> getAttackingSquares(Color playerColor) {
		return attackedPositions.get(playerColor);
	}

	public void updateSquares() {
		attackedPositions.get(Color.WHITE).clear();
		attackedPositions.get(Color.BLACK).clear();

		for (Square square : squares) {
			Piece piece = square.getPiece();
			if (piece == null) continue;
			piece.updateMoves(square.getPosition(), squares);
			attackedPositions.get(piece.getColor()).addAll(piece.getMoves());
		}
	}

	public void playMove(String moveString) throws IllegalMoveException, CommandException {
			Command command = Command.create(moveString, this);
			command.execute();
	}
 }
