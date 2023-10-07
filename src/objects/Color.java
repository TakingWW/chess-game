package objects;

public enum Color {
	BLACK,
	WHITE,
	RED,
	GREEN;

	@Override
	public String toString() {
			switch(this) {
				case BLACK:
					return "\u001B[90m";
				case WHITE:
					return "\u001B[37m";
				case RED:
					return "\u001B[31m";
				case GREEN:
					return "\u001B[32m";
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
					return "b";
				case WHITE:
					return "w";
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

	public String getScapeChar() {
 			return "\u001B[0m";
		}
}
