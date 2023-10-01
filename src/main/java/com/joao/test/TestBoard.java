package com.joao.test;

import com.joao.objects.Board;
import com.joao.objects.Player1;
import com.joao.objects.Player;
import com.joao.objects.IllegalMoveException;
import com.joao.objects.PlayerException;
import com.joao.objects.CommandException;

import java.util.Arrays;
import java.util.ArrayList;

public class TestBoard {
	private Board board;
	private boolean success = true;

	private boolean execute() {
		try {
			Player player = new Player1('w');
			board = new Board(player);
		} catch (PlayerException e) {
			return false;
		}
		return true;
	}

	@Test
	public boolean shouldNotAcceptMoreThanThreeChars() {
		success = false;
		execute();
		try {
			board.playMove("pe42");
		} catch (IllegalMoveException | CommandException e) {
			success = true;
		}
		return success;
	}

	@Test
	public boolean shouldNotAcceptMoveWrongFormat1() {
		success = false;
		execute();
		try {
			board.playMove("   ");
		} catch (IllegalMoveException | CommandException e) {
			success = true;
		}
		return success;
	}

	@Test
	public boolean shouldNotAcceptMoveWrongFormat2() {
		success = false;
		execute();
		try {
			board.playMove("123");
		} catch (IllegalMoveException | CommandException e) {
			success = true;
		}
		return success;
	}

	@Test
	public boolean shouldNotAcceptMoveWrongFormat3() {
		success = false;
		execute();
		try {
			board.playMove("e4p");
		} catch (IllegalMoveException | CommandException e) {
			success = true;
		}
		return success;
	}

	@Test
	public boolean shouldNotAcceptMoveWrongFormat4() {
		success = false;
		execute();
		try {
			board.playMove("qee");
		} catch (IllegalMoveException | CommandException e) {
			success = true;
		}
		return success;
	}

	@Test
	public boolean shouldNotAcceptMoveWrongFormat5() {
		success = false;
		execute();
		try {
			board.playMove("pee");
		} catch (IllegalMoveException | CommandException e) {
			success = true;
		}
		return success;
	}

	@Test
	public boolean testBoardCreate() {
		success = true;
		try {
			Player player = new Player1('w');
			board = new Board(player);
		} catch (PlayerException e) {
			success = false;
		}
		return success;
	}

	@Test
	public boolean testShortRockSequenceShouldFail() {
		execute();
		success = false;

		try {
			board.playMove("pe4");
			board.playMove("pe5");
			board.playMove("bd3");
			board.playMove("bd6");
			board.playMove("nf3");
			board.playMove("nc6");
			board.playMove("o-o");
			board.playMove("o-o");
		} catch (IllegalMoveException | CommandException e) {
			success = true;
		}
		return success;
	}

	@Test
	public boolean testShortRockSequence() {
		execute();
		success = true;
		
		try {
			board.playMove("pe4");
			board.playMove("pe5");
			board.playMove("bd3");
			board.playMove("bd6");
			board.playMove("nf3");
			board.playMove("nf6");
			board.playMove("o-o");
			board.playMove("o-o");
		} catch (IllegalMoveException | CommandException e) {
			success = false;
		}
		return success;
	}

	@Test
	public boolean testLongRockSequenceShouldFail() {
		execute();
		success = false;

		try {
			board.playMove("pb4");
			board.playMove("nc6");
			board.playMove("na3");
			board.playMove("pb6");
			board.playMove("bb2");
			board.playMove("bb7");
			board.playMove("pd3");
			board.playMove("pd6");
			board.playMove("qd2");
			board.playMove("o-o-o");
			board.playMove("o-o-o");
		} catch (IllegalMoveException | CommandException e) {
			success = true;
		}
		return success;
	}

	@Test
	public boolean testLongRockSequence() {
		execute();
		success = true;

		try {
			board.playMove("pb4");
			board.playMove("nc6");
			board.playMove("na3");
			board.playMove("pb6");
			board.playMove("bb2");
			board.playMove("bb7");
			board.playMove("pd3");
			board.playMove("pd6");
			board.playMove("qd2");
			board.playMove("qd7");
			board.playMove("o-o-o");
			board.playMove("o-o-o");
		} catch (IllegalMoveException | CommandException e) {
			success = false;
		}
		return success;
	}

	@Test
	public boolean playRandomMoves() {
		execute();
		success = true;

		try {
			board.playMove("pa3");
			board.playMove("ph5");
			board.playMove("ra2");
			board.playMove("pf5");
			board.playMove("nf3");
			board.playMove("rh6");
		} catch (IllegalMoveException | CommandException e) {
			success = false;
		}
		
		return success;
	}

	@Test
	public boolean playRandomMovesShouldFail() {
		execute();
		success = false;

		try {
			board.playMove("pa3");
			board.playMove("pg5");
			board.playMove("ra2");
			board.playMove("pf4");
			board.playMove("rh6");
		} catch (IllegalMoveException | CommandException e) {
			success = true;
		}
		
		return success;
	}

	@Test
	public boolean cannotRockPassingTrhoughAttackingSquare() {
		execute();
		success = false;

		try {
			board.playMove("pe4");
			board.playMove("pe5");
			board.playMove("nf3");
			board.playMove("nh6");
			board.playMove("pb3");
			board.playMove("ba3");
			board.playMove("ba3");
			board.playMove("o-o");
		} catch (IllegalMoveException | CommandException e) {
			success = true;
		}
		
		return success;
	}

	@Test
	public boolean realGameTest1() {
		execute();
		success = true;
		try {
			board.testArguments = new ArrayList<>(Arrays.asList("0", "1", "0", "0", "1"));
			board.playMove("pe4");
			board.playMove("pc6");
			board.playMove("pd4");
			board.playMove("pd5");
			board.playMove("pe5");
			board.playMove("bf5");
			board.playMove("nd2");
			board.playMove("pe6");
			board.playMove("nb3");
			board.playMove("nd7");
			board.playMove("nf3");
			board.playMove("ne7");
			board.playMove("be2");
			board.playMove("bg4");
			board.playMove("o-o");
			board.playMove("bf3");
			board.playMove("bf3");
			board.playMove("nf5");
			board.playMove("pc3");
			board.playMove("pc5");
			board.playMove("re1");
			board.playMove("be7");
			board.playMove("pc5");
			board.playMove("nc5");
			board.playMove("nc5");
			board.playMove("bc5");
			board.playMove("bf4");
			board.playMove("qb6");
			board.playMove("qc2");
			board.playMove("o-o");
			board.playMove("rd1");//a
			board.playMove("rd8");//f
			board.playMove("bg4");
			board.playMove("ne7");
			board.playMove("pb3");
			board.playMove("rc8");//a
			board.playMove("qe2");
			board.playMove("ng6");
			board.playMove("bg5");
			board.playMove("be7");
			board.playMove("be3");
			board.playMove("qc7");
			board.playMove("bd4");
			board.playMove("bc5");
			board.playMove("pg3");
			board.playMove("bd4");
			board.playMove("pd4");
			board.playMove("qc2");
			board.playMove("rd2");
			board.playMove("qc3");
			board.playMove("ph4");
			board.playMove("ne7");
			board.playMove("rd3");
			board.playMove("qa5");
			board.playMove("pa4");
			board.playMove("rc3");
			board.playMove("rd1");//e
			board.playMove("rc8");//d
			board.playMove("qd2");
			board.playMove("qb4");
			board.playMove("rc3");
			board.playMove("rc3");
			board.playMove("kh2");
			board.playMove("rb3");
			board.playMove("qc2");
			board.playMove("rc3");
			board.playMove("rb1");
			board.playMove("rc2");
			board.playMove("rb4");
			board.playMove("pb6");
			board.playMove("pa5");
			board.playMove("nc6");
			board.playMove("rb5");
			board.playMove("na5");
			board.playMove("kg2");
			board.playMove("rc4");
			board.playMove("be2");
			board.playMove("rd4");
			board.playMove("rb1");
			board.playMove("rd2");
			board.playMove("rc1");
			board.playMove("pg6");
			board.playMove("rc8");
			board.playMove("kg7");
			board.playMove("bf3");
			board.playMove("nc4");
			board.playMove("pg4");
			board.playMove("ne5");
			board.playMove("rc7");
			board.playMove("nd3");
			board.playMove("kg3");
			board.playMove("nf2");
			board.playMove("pg5");
			board.playMove("ne4");
			board.playMove("kf4");
			board.playMove("rf2");
			board.playMove("ra7");
			board.playMove("pb5");
			board.playMove("ke3");
			board.playMove("rf1");
			board.playMove("be4");
			board.playMove("re1");
			board.playMove("kd2");
			board.playMove("re4");
			board.playMove("rb7");
			board.playMove("rc4");
			board.playMove("ph5");
			board.playMove("ph5");
			board.playMove("rb5");
			board.playMove("rg4");
			board.playMove("ke3");
			board.playMove("rg5");
			board.playMove("rb7");
			board.playMove("kg6");
			board.playMove("kd4");
			board.playMove("kf5");
			board.playMove("kc5");
			board.playMove("rg4");
			board.playMove("kd6");
			board.playMove("pf6");
			board.playMove("rf7");
			board.playMove("re4");
			board.playMove("rh7");
			board.playMove("pd4");
			board.playMove("rh5");
			board.playMove("kf4");
		} catch (IllegalMoveException | CommandException e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}
}
