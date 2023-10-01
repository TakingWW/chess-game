package com.joao;

import com.joao.objects.Player1;
import com.joao.objects.Player;
import com.joao.objects.Board;
import com.joao.objects.PlayerException;
import com.joao.objects.IllegalMoveException;
import com.joao.test.Tester;
import com.joao.test.TestException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws PlayerException, TestException {
//		new Tester();
		execute();
    }

	public static void execute() throws PlayerException {
		System.out.printf("Let's start the chess game\n");
		System.out.print("Play with a bot? (Yes/No): ");
		Player player;
		try {
			String otherPlayerFlag = System.console().readLine();
			System.out.print("Choose your pieces color, W to white B to black: ");
			char color = (char) System.in.read();
			player = new Player1(otherPlayerFlag, color);
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
			} catch (IllegalMoveException e) {
				e.printStackTrace();
			}
		}		
	}
}
