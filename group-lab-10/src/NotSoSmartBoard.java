import java.util.*;

public class NotSoSmartBoard extends BoardBase {
    @Override
    public void setupNewBoard(String wordListFilename, int rows, int columns) {
        super.setupNewBoard(wordListFilename, rows, columns);
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

    protected boolean dictContains(String word) {
        for (int i = 0; i < dictionary.length; ++i) {
            if (word.equals(dictionary[i])) {
                return true;
            }
        }
        return false;
    }
}