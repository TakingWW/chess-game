package objects;

import objects.player.Player;

public abstract class Util {
	public static boolean gameOver = false;
	public static Player winner = null;
	public static boolean test = false;

	public static void print(String message) {
		if (!test) System.out.println(message);
	}

	public static void log(String highlight, Color color, String message) {
		System.out.println(color + highlight + color.getScapeChar() + " " + message);
	}

	public static void printf(String message) {
		if (!test) System.out.printf(message);
	}
}
