package objects.command;

import objects.Board;
import static objects.Util.*;

public class FairFaith extends Command {
	private Board board;
	public FairFaith(Board board) {
		this.board = board;
	}
	public void execute() throws CommandException {
		gameOver = true;
		winner = board.getNotToPlay();
	}
}
