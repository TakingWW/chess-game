import static objects.Util.*;
import objects.player.Player1;
import objects.player.Player;
import objects.Board;
import objects.player.PlayerException;
import objects.IllegalMoveException;
import net.SocketClient;
import test.Tester;
import test.TestException;
import objects.command.CommandException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.io.File;

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
				case "net":
					SocketClient client = new SocketClient();
					client.run();

					break;
				default:
					print("No argument valid has been passed, try \'./build.sh test\' or \'./build.sh run\'");
					return;
			}
		} else {
			print("No argument has been passed, try \'test\' or \'run\'");
			print("For example you could run the following command \'./build.sh run\'");
		}
    }

	public static void execute() throws PlayerException {
		printf("Let's start the chess game\nChoose your pieces color, W to white, B to black: ");

		String color = System.console().readLine();

		Player player = new Player1(color);
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
