public class Minesweeper {
    public static void main(String[] args) {
        int size = 9;
        int max_mines = 5; // maximum number of mines in one square
        int[][] mines = new int[size][size];
        // memset 0
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                mines[i][j] = 0;
            }
        }

        // initialize mine field
        int numberOfMines = 10;
        for (int i = 0; i < numberOfMines; i++) {
            while (true) {
                int x = (int) Math.floor(Math.random() * (size - 1));
                int y = (int) Math.floor(Math.random() * (size - 1));
                int n_mines = (int) Math.floor(Math.random() * (max_mines - 1));

                if (mines[x][y] == 0) {
                    mines[x][y] = n_mines;
                    break;
                }
            }
        }

        // calculate counts for surrounding mines
        int[][] counts = new int[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (mines[x][y] > 0) {
                    for (int x1 = Math.max(0, x - 1); x1 <= Math.min(size - 1, x + 1); x1++) {
                        for (int y1 = Math.max(0, y - 1); y1 <= Math.min(size - 1, y + 1); y1++) {
                            counts[x1][y1] += mines[x][y];
                        }
                    }
                }
            }
        }

        int stepcounter = 0;
        // start the user at a square without a mine
        int xCursor = 0;
        int yCursor = 0;
        boolean found_start = false;
        for (xCursor = 0; xCursor < size; ++xCursor) {
            for (yCursor = 0; yCursor < size; ++yCursor) {
                if (mines[xCursor][yCursor] == 0) {
                    found_start = true;
                    break;
                }
            }
            if (found_start) {
                break;
            }
        }

        boolean[][] revealed = new boolean[size][size];
        while (true) {
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    System.out.print(xCursor == x && yCursor == y ? ">" : " ");
                    System.out.print(revealed[x][y] ? (counts[x][y] == 0 ? "_" : counts[x][y]) : "X");
                }
                System.out.println();
            }

            System.out.print("(u)p, (d)own, (l)eft, (r)ight, (s)tep? ");
            char ch = StdIn.readChar();
            System.out.println();
            if (ch == 'u') {
                --yCursor;
            }
            if (yCursor < 0) {
                yCursor = size - 1;
            } else if (ch == 'd') {
                ++yCursor;
                if (yCursor > size - 1) {
                    yCursor = 0;
                }
            } else if (ch == 'l') {
                --xCursor;
                if (xCursor < 0) {
                    xCursor = size - 1;
                }
            } else if (ch == 'r') {
                ++xCursor;
                if (xCursor < 0) {
                    xCursor = size - 1;
                }
            }
            if (xCursor > size - 1) {
                xCursor = 0;
            } else if (ch == 's') {
                if (mines[xCursor][yCursor] > 0) {
                    System.out.println("Sorry.. you stepped on a mine");
                    break;
                }
                revealed[xCursor][yCursor] = true;
                stepcounter++;
            }
            System.out.println("You have cleared " + stepcounter + " square(s)");
        }

        // game over
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                System.out.print(" ");
                System.out.print(mines[x][y] > 0 ? "*" + mines[x][y] : (counts[x][y] == 0 ? "_" : counts[x][y]));
            }
            System.out.println();
        }
    }
}