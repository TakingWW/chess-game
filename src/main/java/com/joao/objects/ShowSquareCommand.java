package com.joao.objects;

import java.util.List;

public class ShowSquareCommand extends Command {
	private Square square;
	
	public ShowSquareCommand(String squareString, Board board) throws CommandException {
		for (Square s : board.getSquares()) {
			try {
				if (s.compareCoordinates(squareString))	this.square = s;
			} catch (NumberFormatException e) {
				throw new CommandException("Wrong argument passed");
			}
		}
	}

	@Override
	public void execute() throws CommandException {
		try {
			System.out.println("moves -> " + square.getPiece().getMoves());
		} catch (NullPointerException e) {
			throw new CommandException("No piece in this square");
		}
	}
}
