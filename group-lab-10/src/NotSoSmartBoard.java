import java.util.*;

public class NotSoSmartBoard implements Board {
    public void setupNewBoard(String wordListFilename, int rows, int columns) {
        this.n_rows = rows;
        this.n_cols = columns;
        this.grid = new char[rows][columns];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                grid[i][j] = '0';
            }
        }
        In filein = new In(wordListFilename);
        this.dictionary = filein.readAllLines();
        this.dictSet = new HashSet<String>(Arrays.asList(this.dictionary));
    }

    public int getRows() {
        return n_rows;
    }

    public int getColumns() {
        return n_cols;
    }

    public char getLetterAt(int row, int col) {
        return this.grid[row][col];
    }

    public String playWord(Play p, boolean dryRunOnly) {
        String word = p.getWord();
        word = word.toLowerCase();
        int w_r = p.getRow();
        int w_c = p.getCol();
        boolean isVertical = p.isVertical();
        // make a copy of grid to check without actually modifying it
        char[][] new_grid = new char[n_rows][n_cols];
        for (int i = 0; i < n_rows; ++i) {
            for (int j = 0; j < n_cols; ++j) {
                new_grid[i][j] = this.grid[i][j];
            }
        }

        ArrayList<Integer> overlappingPts = new ArrayList<Integer>();
        // place in word
        int r, c;
        for (int i = 0; i < word.length(); ++i) {
            if (isVertical) {
                r = w_r + i;
                c = w_c;
            } else {
                r = w_r;
                c = w_c + i;
            }
            // remember where the overlapping is
            if (new_grid[r][c] == word.charAt(i)) {
                overlappingPts.add(i);
            } else {
                new_grid[r][c] = word.charAt(i);
            }
        }
        // check the whole board
        // check all rows
        for (int i = 0; i < n_rows; ++i) {
            ArrayList<String> seqs = this.getMaxLenSequence(this.rowAt(new_grid, i));
            for (String s : seqs) {
                if (s.length() > 1 && !this.dictSet.contains(s)) {
                    return null;
                }
            }
        }

        // check all cols
        for (int i = 0; i < n_cols; ++i) {
            ArrayList<String> seqs = this.getMaxLenSequence(this.colAt(new_grid, i));
            for (String s : seqs) {
                if (s.length() > 1 && !this.dictSet.contains(s)) {
                    return null;
                }
            }
        }
        if (!dryRunOnly) {
            this.grid = new_grid;
        }

        // remove overlapped characters from string
        for (int i : overlappingPts) {
            word = word.substring(0, i) + word.substring(i + 1);
        }
        return word;
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < this.n_rows; ++i) {
            for (int j = 0; j < this.n_rows; ++j) {
                if (grid[i][j] == '0') {
                    result += "*";
                } else {
                    result += "" + grid[i][j];
                }
            }
            result += "\n";
        }
        return result;
    }

    private char[] rowAt(char[][] grid, int index) {
        return grid[index];
    }

    private char[] colAt(char[][] grid, int index) {
        char[] result = new char[this.n_rows];
        for (int i = 0; i < n_rows; ++i) {
            result[i] = grid[i][index];
        }
        return result;
    }

    private ArrayList<String> getMaxLenSequence(char[] charSeq) {
        ArrayList<String> results = new ArrayList<String>();
        String s = "";
        boolean started = false;
        for (int i = 0; i < charSeq.length; ++i) {
            if (started) {
                if (charSeq[i] == '0') {
                    started = false;
                    results.add(s);
                    s = new String();
                } else {
                    s += "" + charSeq[i];
                }
            } else {
                if (charSeq[i] != '0') {
                    started = true;
                    s += "" + charSeq[i];
                }
            }
        }
        return results;
    }

    private char[][] grid;
    private int n_rows, n_cols;
    private String[] dictionary;
    private HashSet<String> dictSet;
}