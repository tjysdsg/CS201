package app;

import java.util.*;

public abstract class BoardBase implements Board {
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

    public abstract String playWord(Play p, boolean dryRunOnly);

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

    protected ArrayList<String> getMaxLenSequence(char[] charSeq) {
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

    protected abstract boolean dictContains(String word);

    protected char[][] grid;
    protected int n_rows, n_cols;
    protected String[] dictionary;
}