import objects.player.Player1;
import objects.player.Player;
import objects.Board;
import objects.player.PlayerException;
import objects.IllegalMoveException;
import test.Tester;
import test.TestException;
import objects.command.CommandException;
import static objects.Util.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws PlayerException, TestException {
		if (args.length > 0) {
			switch(args[0]) {
				case "test":
					test = true;
					new Tester();
					break;
				case "run":
					execute();
					break;
				default:
					print("No argument valid has been passed, try \'test\' or \'run\'");
					break;
			}
		}
    }

	public static void execute() throws PlayerException {
		printf("Let's start the chess game\n");
		Player player;

		try {
			printf("Choose your pieces color, W to white B to black: ");
			char color = (char) System.in.read();
			player = new Player1(color);
		} catch (IOException e) {
			throw new PlayerException("Could not create the player instance");
		}

		Board board = new Board(player);

		while (!gameOver) {
			board.draw();
			printf(board.getToPlay().getColor().print() + " to play: ");
			String lance = System.console().readLine();
			try {
				board.playMove(lance);
			} catch (IllegalMoveException | CommandException e) {
				e.printStackTrace();
			}
		}
		
		if (winner != null) print(winner.getColor().print() + " pieces won");
		else print("We don't have a winner");
	}
}
