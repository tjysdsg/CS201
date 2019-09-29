import java.io.File;

public class TestDriver {
	public static void main(String[] args) {
		System.out.println("\n---------------------\ntesting smart board");
		Board smartBoard = new SmartBoard();
		testBoard(smartBoard);

		System.out.println("\n---------------------\ntesting not so smart board");
		Board notSoSmartBoard = new NotSoSmartBoard();
		testBoard(notSoSmartBoard);
	}

	public static void testBoard(Board board, boolean printBoard) {
		Stopwatch s = new Stopwatch();

		File f = new File("sowpods.txt");
		if (f.isFile()) {
			board.setupNewBoard("sowpods.txt", 10, 10);
		} else {
			board.setupNewBoard("https://www.wordgamedictionary.com/sowpods/download/sowpods.txt", 10, 10);
		}

		double boardTime = s.elapsedTime();

		System.out.println("Playing word Duke horizontally at 4,0 used letters "
				+ board.playWord(new Play(4, 0, "Duke", false), false) + " (should be duke)");
		if (printBoard) {
			System.out.println(board);
		}

		System.out.println("Playing word compUter vertically at 0,1 used letters "
				+ board.playWord(new Play(0, 1, "compUter", true), false) + " (should be compter)");
		if (printBoard) {
			System.out.println(board);
		}

		System.out.println("Playing word science horizontally at 8,1 used letters "
				+ board.playWord(new Play(8, 1, "science", false), false) + " (should be science)");
		if (printBoard) {
			System.out.println(board);
		}

		System.out.println("Playing word lab horizontally at 8,1 used letters "
				+ board.playWord(new Play(8, 1, "lab", false), false) + " (should be null -- not allowed)");
		if (printBoard) {
			System.out.println(board);
		}

		double totalTime = s.elapsedTime();
		System.out.println("Time to setup board: " + String.format("%.5e", boardTime) + " ns");
		System.out.println("Time to play game: " + String.format("%.5e", totalTime - boardTime) + " ns");
		System.out.println("Total time: " + String.format("%.5e", totalTime) + " ns");
	}

	public static void testBoard(Board board) {
		testBoard(board, false);
	}
}