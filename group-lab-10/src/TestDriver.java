public class TestDriver {
	public static void main(String[] args) {
		System.out.println("\n---------------------\ntesting smart board");
		// Board smartBoard = new SmartBoard();
		// testBoard(smartBoard);

		System.out.println("\n---------------------\ntesting not so smart board");
		Board notSoSmartBoard = new NotSoSmartBoard();
		testBoard(notSoSmartBoard);
	}

	public static void testBoard(Board board) {
		Stopwatch s = new Stopwatch();

		// board.setupNewBoard("https://www.wordgamedictionary.com/sowpods/download/sowpods.txt",
		// 10, 10);
		// TIP: download the file and save it to the project directory to save a lot of
		// time; then, comment out the line above and un-comment the line below
		board.setupNewBoard("sowpods.txt", 10, 10);

		double boardTime = s.elapsedTime();

		System.out.println("Playing word Duke horizontally at 4,0 used letters "
				+ board.playWord(new Play(4, 0, "Duke", false), false) + " (should be duke)");
		System.out.println(board);

		System.out.println("Playing word compUter vertically at 0,1 used letters "
				+ board.playWord(new Play(0, 1, "compUter", true), false) + " (should be compter)");
		System.out.println(board);

		System.out.println("Playing word science horizontally at 8,1 used letters "
				+ board.playWord(new Play(8, 1, "science", false), false) + " (should be science)");
		System.out.println(board);

		System.out.println("Playing word lab horizontally at 8,1 used letters "
				+ board.playWord(new Play(8, 1, "lab", false), false) + " (should be null -- not allowed)");
		System.out.println(board);

		double totalTime = s.elapsedTime();
		System.out.println("Time to setup board: " + (int) (boardTime * 1000) + " ms");
		System.out.println("Time to play game: " + (int) ((totalTime - boardTime) * 1000) + " ms");
		System.out.println("Total time: " + (int) (totalTime * 1000) + " ms");
	}
}