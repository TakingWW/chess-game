package com.joao.objects;

public enum Color {
	BLACK,
	WHITE;

	@Override
	public String toString() {
			switch(this) {
				case BLACK:
					return "\u001B[90m";
				case WHITE:
					return "\u001B[37m";
				default:
					return "";
			}
		}

	public int getUnit() {
			switch(this) {
				case BLACK:
					return -1;
				case WHITE:
					return 1;
				default:
					return 0;
			}
		}

	public String print() {
			switch(this) {
				case BLACK:
					return "black";
				case WHITE:
					return "white";
				default:
					return "";
			}
		}
	public Color getOposite() {
			switch(this) {
				case BLACK:
					return WHITE;
				case WHITE:
					return BLACK;
				default:
					return null;
			}
		}
}
