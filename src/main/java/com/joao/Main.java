package com.joao;

import com.joao.objects.Player1;
import com.joao.objects.Player;
import com.joao.objects.Board;
import com.joao.objects.PlayerException;
import com.joao.objects.IllegalMoveException;
import com.joao.test.Tester;
import com.joao.test.TestException;
import com.joao.objects.CommandException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws PlayerException, TestException {
		if (args.length > 0) {
			switch(args[0]) {
				case "test":
					new Tester();
					break;
				case "run":
					execute();
					break;
				default:
					System.out.println("No argument valid has been passed, try \'test\' or \'run\'");
					break;
			}
		}
    }

	public static void execute() throws PlayerException {
		System.out.printf("Let's start the chess game\n");
		Player player;
		try {
//			String otherPlayerFlag = System.console().readLine();
			System.out.print("Choose your pieces color, W to white B to black: ");
			char color = (char) System.in.read();
			player = new Player1(color);
		} catch (IOException e) {
			throw new PlayerException("Could not create the player instance");
		}

		Board board = new Board(player);

		while (!board.gameOver) {
			board.draw();
			System.out.print(board.getToPlay().getColor().print() + " to play: ");
			String lance = System.console().readLine();
			try {
				board.playMove(lance);
			} catch (IllegalMoveException | CommandException e) {
				e.printStackTrace();
			}
		}		
	}
}
