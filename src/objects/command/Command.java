package objects.command;

import objects.Board;
import objects.Move;
import objects.IllegalMoveException;

public abstract class Command {
	public static Command create(String command, Board board) throws CommandException, IllegalMoveException {
		if (command.length() == 0) throw new CommandException("Null string");

		String[] args = command.split(" ");
		Command cmd;
		
		if (args.length == 0) throw new CommandException("Cannot parse empty string");

		switch(args[0]) {
			case "/show":
				if (args.length < 2) throw new CommandException("No argument has been passed");
				cmd = new ShowSquareCommand(args[1], board);
				break;
			case "/draw":
				cmd = new DrawCommand();
				break;
			case "/ff":
				cmd = new FairFaith(board);
				break;
			default:
				cmd = new Move(command, board);
		}
		return cmd;
	}

	public abstract void execute() throws CommandException, IllegalMoveException;
}
