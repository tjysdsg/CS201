package app;

import java.util.*;

public class OurGroupBoard extends BoardBase {
    @Override
    public void setupNewBoard(String wordListFilename, int rows, int columns) {
        super.setupNewBoard(wordListFilename, rows, columns);
        this.grid = new char[rows * columns];
        for (int i = 0; i < rows * columns; ++i) {
            this.grid[i] = '0';
        }
        List<String> dictSorted = Arrays.asList(this.dictionary);
        Collections.sort(dictSorted);
        this.dictionary = dictSorted.toArray(new String[0]);
    }

    public String playWord(Play p, boolean dryRunOnly) {
        String word = p.getWord();
        word = word.toLowerCase();
        int w_r = p.getRow();
        int w_c = p.getCol();
        boolean isVertical = p.isVertical();
        // check if the word is out of bound
        if (isVertical) {
            int new_r = w_r + word.length() - 1;
            if (new_r >= this.n_rows) {
                return null;
            }
        } else {
            int new_c = w_c + word.length() - 1;
            if (new_c >= this.n_cols) {
                return null;
            }
        }
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
            } else if (new_grid[r * n_cols + c] == '0') {
                new_grid[r * n_cols + c] = word.charAt(i);
            } else {
                return null;
            }
        }
        // check the part of the board that has been modified
        ArrayList<Integer> rows2Check = new ArrayList<Integer>();
        ArrayList<Integer> cols2Check = new ArrayList<Integer>();
        rows2Check.add(w_r);
        cols2Check.add(w_c);
        for (int i = 1; i < word.length(); ++i) {
            if (isVertical) {
                rows2Check.add(w_r + i);
            } else {
                cols2Check.add(w_c + i);
            }
        }
        for (int i : rows2Check) {
            ArrayList<String> seqs = this.getMaxLenSequence(this.rowAt(new_grid, i));
            for (String s : seqs) {
                if (s.length() > 1 && !this.dictContains(s)) {
                    return null;
                }
            }
        }
        for (int i : cols2Check) {
            ArrayList<String> seqs = this.getMaxLenSequence(this.colAt(new_grid, i));
            for (String s : seqs) {
                if (s.length() > 1 && !this.dictContains(s)) {
                    return null;
                }
            }
        }

        // place in word
        if (!dryRunOnly) {
            this.grid = new_grid;
        }

        // remove overlapped characters from string
        if (overlappingPts.size() > 0) {
            Collections.sort(overlappingPts);
            String result = "";
            int n_pts = overlappingPts.size();
            for (int i = 0; i < n_pts - 1; ++i) {
                result += word.substring(overlappingPts.get(i), overlappingPts.get(i + 1));
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
        return searchString(word) != -1;
    }

    public int searchString(String search) {
        return searchString(search, 0, dictionary.length - 1);
    }

    // [start, end]
    public int searchString(String search, int start, int end) {
        if (start > end) {
            return -1;
        }
        int mid = (start + end) / 2;
        if (search.compareTo(dictionary[mid]) == 0) {
            return mid;
        }
        if (search.compareTo(dictionary[mid]) > 0) {
            return searchString(search, mid + 1, end);
        } else {
            return searchString(search, start, mid - 1);
        }
    }

    private char[] grid;
}