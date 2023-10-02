package objects.command;

import java.util.List;

import objects.Square;
import objects.Board;
import static objects.Util.*;

public class ShowSquareCommand extends Command {
	private Square square;
	
	public ShowSquareCommand(String squareString, Board board) throws CommandException {
		for (Square s : board.getSquares()) {
			try {
				if (s.compareCoordinates(squareString))	this.square = s;
				if (squareString.length() != 2) throw new NumberFormatException();
			} catch (NumberFormatException e) {
				throw new CommandException("Wrong argument passed");
			}
		}
	}

	@Override
	public void execute() throws CommandException {
		try {
			print("moves -> " + square.getPiece().getMoves());
		} catch (NullPointerException e) {
			throw new CommandException("No piece in this square");
		}
	}
}
