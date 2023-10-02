package objects.command;

import java.util.ArrayList;
import java.util.List;

import objects.piece.Piece;
import objects.Board;

public abstract class Command {
	enum CMD {
		SHOW_SQUARE,
		DRAW,
		FAIR_FAITH,
	}

	public static Command create(String command, Board board) throws CommandException {
		String[] args = command.split(" ");
		Command cmd;
		if (args.length == 0) throw new CommandException("Not command has been passed");
		switch(args[0]) {
			case "show":
				if (args.length < 2) throw new CommandException("No argument has been passed");
				cmd = new ShowSquareCommand(args[1], board);
				break;
			case "draw":
				cmd = new DrawCommand();
				break;
			case "ff":
				cmd = new FairFaith(board);
				break;
			default:
				cmd = null;
		}
		return cmd;
	}
		
	public abstract void execute() throws CommandException;
}
