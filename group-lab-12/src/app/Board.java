package app;

/**
 * Abstract interface representing a Words board.
 */
public interface Board {
	/**
	 * Resets and initializes a new board of with the given number of rows and
	 * columns and loads a word list from the supplied filename/URL.
	 */
	public void setupNewBoard(String wordListFilename, int rows, int columns);

	/**
	 * @return the number of rows on the board
	 */
	public int getRows();

	/**
	 * @return the number of columns on the board
	 */
	public int getColumns();

	/**
	 * @return the letter at the given row and column, or 0 if no letter has been
	 *         played yet at that location. Letters returned are always lowercase.
	 */
	public char getLetterAt(int row, int col);

	/**
	 * Places a word onto the board. A word can only be placed if it results in a
	 * board where all contiguous sequences of letters, vertical or horizontal, are
	 * valid words present in the word list given during setup (only the longest
	 * sequences in any position are considered; shorter sub-sequences do not need
	 * to be valid words). Words are converted to lowercase during play. If the word
	 * to be played overlaps with an existing word, the overlapping letters must be
	 * identical for the play to be legal.
	 * 
	 * @param dryRunOnly set to true to not actually place the word on the board but
	 *                   just calculate which letters would be played or to test for
	 *                   a legal move
	 * @return the letters played (not counting overlapping letters that were played
	 *         previously), or null if the play was not legal
	 */
	public String playWord(Play p, boolean dryRunOnly);

	//
	public String toString();
}