package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;

public class TestDriver2 {
	public static final String wordListFilename = "sowpods.txt";

	public static class Player {
		private WordFinder wordFinder;
		private ArrayList<String> hand = new ArrayList<>();
		public int score;

		public Player(WordFinder wordFinder) {
			this.wordFinder = wordFinder;
		}

		public WordFinder getWordFinder() {
			return this.wordFinder;
		}

		public ArrayList<String> getHand() {
			return this.hand;
		}

		public int getScore() {
			return this.score;
		}

		public void addToScore(int n) {
			this.score += n;
		}
	}

	public static int playGame(Board b, List<Player> players) {
		int handSize = 7;
		int rounds = 0;
		LinkedList<String> pool = new LinkedList<>();

		// TODO try different variations for letter pool to test your code
		pool.addAll(Arrays.asList("a b c d e".split(" ")));
		pool.addAll(Arrays.asList("f g h i j k l m n o p q r s t u v w".split(" ")));
		pool.addAll(Arrays.asList("x y z".split(" ")));
		pool.addAll(Arrays.asList("a e i o".split(" ")));

		while (true) {
			int playerIndex = rounds % players.size();
			Player player = players.get(playerIndex);
			ArrayList<String> hand = player.getHand();
			System.out.println("\n\nround " + (rounds++) + " player " + playerIndex + "\n================\n");
			int j = hand.size();
			while (hand.size() < handSize && pool.size() > 0) {
				hand.add(pool.removeFirst());
			}
			if (j < hand.size()) {
				System.out.println("added letters " + hand.subList(j, hand.size()));
			}

			SortedSet<WordFinder.Candidate> results = player.getWordFinder().findCandidates(b, hand);
			if (results.size() == 0) {
				System.out.println("game over - could not find word to play");
				return playerIndex;
			}

			WordFinder.Candidate candidate = results.first();
			String lettersPlayed = b.playWord(candidate.getPlay(), false);
			if (lettersPlayed == null) {
				System.out.println("game over - tried to play invalid word");
				return playerIndex;
			}
			if (lettersPlayed.length() == 0) {
				System.out.println("game over - played word that did not use any letters");
				return playerIndex;
			}

			player.addToScore(lettersPlayed.length());
			System.out.println("playing " + candidate);
			System.out.println(b);
			for (int i = 0; i < lettersPlayed.length(); i++) {
				hand.remove("" + lettersPlayed.charAt(i));
			}
			System.out.println("score " + player.getScore());
			System.out.println("letters left " + hand);
		}
	}

	public static void main(String[] args) {
		Board b = new OurGroupBoard();
		b.setupNewBoard(wordListFilename, 10, 10);

		b.playWord(new Play(4, 4, "duke", false), false);

		ArrayList<Player> players = new ArrayList<>();

		players.add(new Player(new Group04WordFinder()));
		for (Player p : players) {
			p.getWordFinder().setupIndex(wordListFilename, 10);
		}

		int lastPlayerIndex = playGame(b, players);
		Player lastPlayer = players.get(lastPlayerIndex);
		if (lastPlayer.getHand().size() > 0) {
			System.out.println("deducting " + lastPlayer.getHand().size() + " points from remaining letters in hand");
			lastPlayer.addToScore(-lastPlayer.getHand().size());
		}

		System.out.println("\n\nfinal scores\n============\n");
		for (int i = 0; i < players.size(); i++) {
			System.out.println("player " + i + " score " + players.get(i).getScore());
		}
	}
}