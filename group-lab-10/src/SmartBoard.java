import java.util.*;

public class SmartBoard extends BoardBase {
    @Override
    public void setupNewBoard(String wordListFilename, int rows, int columns) {
        this.n_rows = rows;
        this.n_cols = columns;
        In filein = new In(wordListFilename);
        this.grid = new char[rows * columns];
        for (int i = 0; i < rows * columns; ++i) {
            this.grid[i] = '0';
        }
        dictionary = filein.readAllLines();
        this.dictSet = new HashSet<String>(Arrays.asList(dictionary));
    }

    public String playWord(Play p, boolean dryRunOnly) {
        String word = p.getWord();
        word = word.toLowerCase();
        int w_r = p.getRow();
        int w_c = p.getCol();
        boolean isVertical = p.isVertical();
        // make a copy of grid to check without actually modifying it
        char[] new_grid = new char[n_rows * n_cols];
        for (int i = 0; i < n_rows * n_cols; ++i) {
            new_grid[i] = this.grid[i];
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
            if (new_grid[r * n_cols + c] == word.charAt(i)) {
                overlappingPts.add(i);
            } else {
                new_grid[r * n_cols + c] = word.charAt(i);
            }
        }
        // check the whole board
        // check all rows
        for (int i = 0; i < n_rows; ++i) {
            ArrayList<String> seqs = this.getMaxLenSequence(this.rowAt(new_grid, i));
            for (String s : seqs) {
                if (s.length() > 1 && !this.dictContains(s)) {
                    return null;
                }
            }
        }

        // check all cols
        for (int i = 0; i < n_cols; ++i) {
            ArrayList<String> seqs = this.getMaxLenSequence(this.colAt(new_grid, i));
            for (String s : seqs) {
                if (s.length() > 1 && !this.dictContains(s)) {
                    return null;
                }
            }
        }
        if (!dryRunOnly) {
            this.grid = new_grid;
        }

        // remove overlapped characters from string
        if (overlappingPts.size() > 0) {
            String result = "";
            result += word.substring(0, overlappingPts.get(0)) + word.substring(overlappingPts.get(0) + 1);
            for (int i = 1; i < overlappingPts.size(); ++i) {
                result += word.substring(overlappingPts.get(i - 1) + 1, overlappingPts.get(i));
            }
            if (overlappingPts.size() > 1) {
                result += word.substring(overlappingPts.get(overlappingPts.size() - 1) + 1);
            }
            return result;
        } else {
            return word;
        }
    }

    private char[] rowAt(char[] grid, int index) {
        char[] result = new char[this.n_cols];
        for (int i = 0; i < n_rows; ++i) {
            result[i] = grid[index * n_cols + i];
        }
        return result;
    }

    private char[] colAt(char[] grid, int index) {
        char[] result = new char[this.n_rows];
        for (int i = 0; i < n_rows; ++i) {
            result[i] = grid[i * n_cols + index];
        }
        return result;
    }


    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < n_rows * n_cols; ++i) {
            if (grid[i] == '0') {
                result += "*";
            } else {
                result += "" + grid[i];
            }
            if ((i + 1) % n_cols == 0) {
                result += "\n";
            }
        }
        return result;
    }

    protected boolean dictContains(String word) {
        return this.dictSet.contains(word);
    }

    private HashSet<String> dictSet;
    private char[] grid;
}