import java.util.*;

public class SmartBoard implements Board {

    public SmartBoard() {
        this.wordPlacements = new ArrayList<WordPlacement>();
    }

    public void setupNewBoard(String wordListFilename, int n_rows, int n_cols) {
        this.grid = new char[n_rows * n_cols];
        this.n_rows = n_rows;
        this.n_cols = n_cols;

        In filein = new In(wordListFilename);
        this.dictionary = filein.readAllLines();

        this.dictSet = new HashSet<String>(Arrays.asList(this.dictionary));
    }

    public int getRows() {
        return this.n_rows;
    }

    public int getColumns() {
        return this.n_cols;
    }

    public char getLetterAt(int row, int col) {
        return this.grid[row * this.n_rows + col];
    }

    public String playWord(Play p, boolean dryRunOnly) {
        String word = p.getWord();
        // check boundaries
        if (this.isOutOfBound(p.getRow(), p.getCol(), word.length(), p.isVertical())) {
            return null;
        }
        word = word.toLowerCase();
        WordPlacement wp1 = makeWordPlacement(word, p.getRow(), p.getCol(), p.isVertical());
        // check if this is the first word that is being placed
        if (this.wordPlacements.size() > 0) {
            // check overlapping with existing words
            for (int i = 0; i < this.wordPlacements.size(); ++i) {
                // make a copy, since checkNeighborAndOverlapping will modify the
                // content of both arguments
                WordPlacement wp2 = new WordPlacement(this.wordPlacements.get(i));
                if (!checkNeighborAndOverlapping(wp1, wp2)) {
                    return null;
                }
                this.wordPlacements.set(i, wp2);
            }
            wp1.commit();
            if (!dryRunOnly) {
                this.wordPlacements.add(wp1);
                this.placeWordInGrid(wp1);
            }
            return wp1.getWord();
        }
        if (!dryRunOnly) {
            this.wordPlacements.add(wp1);
            this.placeWordInGrid(wp1);
        }
        return wp1.getWord();
    }

    private boolean checkNeighborAndOverlapping(WordPlacement wp1, WordPlacement wp2) {
        // check neighbors for wp2, if there is, prepend/append the string to wp2
        int[] neighborOffsets = this.getNeighborOffsets(wp2);
        int front = wp1.searchIndex(neighborOffsets[0]);
        int back = wp1.searchIndex(neighborOffsets[1]);
        if (front != -1) {
            System.out.print("Found neighbor for " + wp2.getWord() + ", changing it to ");
            wp2.prepend(neighborOffsets[0], wp1.getChar(front));
            System.out.println(wp2.getWord());
        }
        if (back != -1) {
            System.out.print("Found neighbor for " + wp2.getWord() + ", changing it to ");
            wp2.append(neighborOffsets[1], wp1.getChar(back));
            System.out.println(wp2.getWord());
        }
        // check if both words, after possible concatenation, are in the dictionary
        if (!(this.contains(wp1.getWord()) && this.contains(wp2.getWord()))) {
            return false;
        }

        // check overlapping
        // if it's impossile for wp1 and wp2 to come across
        if (wp1.getMinOffset() > wp2.getMaxOffset() || wp1.getMaxOffset() < wp2.getMinOffset()) {
            return true;
        } // otherwise it's possible for them to have a cross point
        else {
            // search for the same offset
            for (int i = 0; i < wp1.length(); ++i) {
                int searched = wp2.searchIndex(wp1.getIndex(i));
                if (searched == -1) {
                    continue;
                }
                if (wp1.getChar(i) != wp2.getChar(searched)) {
                    return false;
                } else {
                    wp1.markForDeletion(i);
                }
            }
        }
        return true;
    }

    private void placeWordInGrid(WordPlacement wp) {
        int len = wp.length();
        for (int i = 0; i < len; ++i) {
            this.grid[wp.getIndex(i)] = wp.getChar(i);
        }
    }

    private boolean contains(String input) {
        // TODO: implement string search
        return this.dictSet.contains(input);
    }

    private boolean isOutOfBound(int row, int col, int len, boolean vertical) {
        if (row >= this.n_rows || col >= this.n_cols) {
            return true;
        }

        if (vertical && row + len > this.n_rows) {
            return true;
        }
        if (!vertical && col + len > this.n_cols) {
            return true;
        }
        return false;
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < this.n_rows; ++i) {
            for (int j = 0; j < this.n_rows; ++j) {
                if (grid[i * n_rows + j] == '\u0000') {
                    result += "*";
                } else {
                    result += "" + grid[i * n_rows + j];
                }
            }
            result += "\n";
        }
        return result;
    }

    private int getOffset(int row, int col) {
        return row * this.n_rows + col;
    }

    private WordPlacement makeWordPlacement(String word, int row, int col, boolean vertical) {
        return new WordPlacement(word, this.getOffset(row, col), vertical, this.n_cols);
    }

    // guarentee that the offsets are within the board if the value is not -1
    private int[] getNeighborOffsets(WordPlacement wp) {
        int front, back;
        if (wp.isVertical()) {
            front = wp.getIndex(0) - n_cols;
            back = wp.getIndex(wp.length() - 1) + n_cols;
        } else {
            front = wp.getIndex(0) - 1;
            back = wp.getIndex(wp.length() - 1) + 1;
        }
        if (front < 0 || front > this.grid.length) {
            front = -1;
        }
        if (back < 0 || back > this.grid.length) {
            back = -1;
        }
        return new int[] { front, back };
    }

    private char[] grid;
    private int n_rows;
    private int n_cols;
    private String[] dictionary;
    private HashSet<String> dictSet;
    private ArrayList<WordPlacement> wordPlacements;
}

class WordPlacement {
    public WordPlacement(String word, int offset, boolean vertical, int n_cols) {
        this.offset = offset;
        this.vertical = vertical;
        this.chars = new ArrayList<>();
        this.offsets = new ArrayList<>();
        int i = offset;
        for (char c : word.toCharArray()) {
            this.offsets.add(i);
            this.chars.add(c);
            if (vertical) {
                i += n_cols;
            } else {
                ++i;
            }
        }

        this.deletion = new ArrayList<>();
    }

    // copy constructor
    public WordPlacement(WordPlacement other) {
        this.offset = other.offset;
        this.vertical = other.vertical;
        this.offsets = other.offsets;
        this.chars = other.chars;
        this.deletion = other.deletion;
    }

    public int length() {
        return offsets.size();
    }

    public String getWord() {
        String result = "";
        for (char c : this.chars) {
            result += c;
        }
        return result;
    }

    public int getMinOffset() {
        return offset;
    }

    public int getMaxOffset() {
        return offsets.get(offsets.size() - 1);
    }

    public int getIndex(int i) {
        return offsets.get(i);
    }

    public char getChar(int i) {
        return chars.get(i);
    }

    public int searchIndex(int search) {
        return searchIndex(search, 0, offsets.size() - 1);
    }

    public void commit() {
        for (int d : this.deletion) {
            this.removeAt(d);
        }
    }

    // [start, end]
    public int searchIndex(int search, int start, int end) {
        if (start > end) {
            return -1;
        }
        int mid = (start + end) / 2;
        if (search == offsets.get(mid)) {
            return mid;
        }
        if (search > offsets.get(mid)) {
            return searchIndex(search, mid + 1, end);
        } else {
            return searchIndex(search, start, mid - 1);
        }
    }

    public void markForDeletion(int i) {
        this.deletion.add(i);
    }

    private void removeAt(int i) {
        this.offsets.remove(i);
        this.chars.remove(i);

    }

    public boolean isVertical() {
        return this.vertical;
    }

    public void prepend(int offset, char c) {
        this.offsets.add(0, offset);
        this.chars.add(0, c);
    }

    public void append(int offset, char c) {
        this.offsets.add(offset);
        this.chars.add(c);
    }

    private ArrayList<Integer> offsets;
    private ArrayList<Character> chars;
    private ArrayList<Integer> deletion;
    private int offset;
    private boolean vertical;
}
