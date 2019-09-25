/**
 * Represents a single word being played at a specific location on the board.
 */
public class Play {
	private final String word;
	private final int row;
	private final int col;
	private final boolean vertical;

	public Play(int row, int col, String word, boolean vertical) {
		this.word = word;
		this.row = row;
		this.col = col;
		this.vertical = vertical;
	}

	/**
	 * @return true if word is to be played vertically; false if horizontally
	 */
	public boolean isVertical() {
		return vertical;
	}

	/**
	 * @return 0-based column
	 */
	public int getCol() {
		return col;
	}

	/**
	 * @return 0-based row
	 */
	public int getRow() {
		return row;
	}

	public String getWord() {
		return word;
	}
}