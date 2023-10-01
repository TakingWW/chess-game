package com.joao.objects;

import java.util.ArrayList;
import java.util.List;

import com.joao.objects.piece.Piece;

public abstract class Command {
	enum CMD {
		SHOW_SQUARE,
		DRAW,
	}


	public static Command create(String command, Board board) throws CommandException {
		String[] args = command.split(" ");
		Command cmd;
		switch(args[0]) {
			case "show":
				cmd = new ShowSquareCommand(args[1], board);
				break;
			default:
				cmd = null;
		}
		return cmd;
	}
		
	public abstract void execute() throws CommandException;
}
