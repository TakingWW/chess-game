package com.joao;

import com.joao.objects.Player1;
import com.joao.objects.Player;
import com.joao.objects.Board;
import com.joao.objects.PlayerException;
import com.joao.objects.IlegalMoveException;

public class Main {
    public static void main(String[] args) throws PlayerException {
	System.out.printf("Let's start the chess game\n");

	Player player = new Player1();
	player.initialize();
	Board board = new Board(player);

	while (!board.gameOver) {
	    board.draw();
	    System.out.print(board.getToPlay().getColor().print() + " to play: ");
	    String lance = System.console().readLine();

	    try {
		board.playMove(lance);
	    } catch (IlegalMoveException e) {
		e.printStackTrace();
	    }
	}
    }
}
