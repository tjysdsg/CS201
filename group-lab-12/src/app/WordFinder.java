package app;

import java.util.List;
import java.util.SortedSet;

public interface WordFinder {
	/**
	 * Represents a potential play and the letters to be used from the player's
	 * hand.
	 */
	public static class Candidate implements Comparable<Candidate> {
		private final Play play;
		private final String lettersPlayed;

		public Candidate(Play play, String lettersPlayed) {
			this.play = play;
			this.lettersPlayed = lettersPlayed;
		}

		public String getLettersPlayed() {
			return lettersPlayed;
		}

		public Play getPlay() {
			return play;
		}

		public String toString() {
			return this.lettersPlayed + " -> " + this.play;
		}

		@Override
		public int compareTo(Candidate o) {
			// Descending order by number of letters played
			return o.lettersPlayed.length() - this.lettersPlayed.length();
		}

		public boolean equals(Object o) {
			if (!(o instanceof Candidate)) {
				return false;
			}

			Candidate c = (Candidate) o;
			return c.lettersPlayed.equals(this.lettersPlayed) && c.play.equals(this.play);
		}
	}

	/**
	 * Initializes the index. Needs to be called only once after creation.
	 * 
	 * @param wordListFilename path or URL to list of words (same format used by
	 *                         Board)
	 * @param maxWordLength    cutoff length for words -- words longer than the
	 *                         cutoff will not be indexed
	 */
	public void setupIndex(String wordListFilename, int maxWordLength);

	/**
	 * Identifies candidate plays given the letters in hand.
	 * 
	 * @return a set of candidates in order from best to worst
	 */
	public SortedSet<Candidate> findCandidates(Board board, List<String> lettersInHand);
}