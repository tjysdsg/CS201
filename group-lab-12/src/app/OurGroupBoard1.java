package app;

public class OurGroupBoard1 implements Board {
    private String[] word_list;
    private char[][] board_dat;
    private int pointer;
    private String return_word;

    public String toString() {
        String tmp = "";
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getColumns(); j++) {
                tmp += String.valueOf(this.board_dat[i][j]);
            }
            tmp += String.valueOf('\n');
        }
        return tmp;
    }

    public void setupNewBoard(String wordlistname, int rows, int columns) {
        In w_l = new In(wordlistname);
        this.word_list = w_l.readAllStrings();
        board_dat = new char[rows][columns];
        for (int i = 0; i < word_list.length; i++)
            if (word_list[i].equals("aa")) {
                pointer = i;
                break;
            }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board_dat[i][j] = '_';
            }
        }
    }

    public String[] wolll() {
        return word_list;
    }

    public int getRows() {
        return board_dat.length;
    }

    public int getColumns() {
        return board_dat[0].length;
    }

    public char getLetterAt(int row, int col) {
        int c = board_dat[row][col] - 'a';
        if (c >= 0)
            return board_dat[row][col];
        return 0;
    }

    public boolean binary_search(String word, String[] wo_lis) {
        int sta = pointer, end = wo_lis.length - 1;
        while (sta <= end) {
            int mid = (sta + end) >>> 1;
            int res = wo_lis[mid].compareTo(word);
            if (res > 0) {
                end = mid - 1;
            } else {
                sta = mid + 1;
            }
            if (res == 0)
                return true;
        }
        return false;
    }

    public boolean check_words(String word) {
        return binary_search(word, this.word_list);
    }

    public String word_overlap(boolean vertical, String word, int row, int col) {
        String tmp = "";
        if (vertical) {
            for (int i = 0; i < word.length(); i++) {
                if (check_board_char(this.board_dat, row + i, col)) {
                    if (this.board_dat[i + row][col] != word.charAt(i))
                        return "0";
                } else
                    tmp += word.charAt(i);
            }
        } else {
            for (int i = 0; i < word.length(); i++) {
                if (check_board_char(this.board_dat, row, col + i)) {
                    if (this.board_dat[row][col + i] != word.charAt(i))
                        return "0";
                } else
                    tmp += word.charAt(i);
            }
        }
        return tmp;
    }

    public boolean check_char(char a) {
        return a != '_';
    }

    public boolean check_board_char(char[][] c, int row, int col) {
        return check_char(c[row][col]);
    }

    public boolean concate_string_v(char[][] board, boolean v, int ii, String word, int row, int col) {
        String tmp = "";
        if (!v)
            tmp = String.valueOf(word.charAt(ii));
        else
            tmp = tmp + word;
        int row0 = row;
        while (row0 > 0) {
            row0--;
            if (!check_board_char(board, row0, col))
                break;
            tmp = board[row0][col] + tmp;
        }
        row0 = (v ? row + word.length() - 1 : row);
        while (row0 < this.getRows() - 1) {
            row0++;
            if (!check_board_char(board, row0, col))
                break;
            tmp += board[row0][col];
        }
        if (tmp.length() == 1)
            return true;
        else
            return check_words(tmp);
    }

    public boolean concate_string_h(char[][] board, boolean h, int ii, String word, int row, int col) {
        String tmp = "";
        if (!h)
            tmp = String.valueOf(word.charAt(ii));
        else
            tmp = tmp + word;
        int col0 = col;
        while (col0 > 0) {
            col0--;
            if (!check_board_char(board, row, col0))
                break;
            tmp = String.valueOf(board[row][col0]) + tmp;
        }
        col0 = (h ? col + word.length() - 1 : col);
        while (col0 < this.getColumns() - 1) {
            col0++;
            if (!check_board_char(board, row, col0))
                break;
            tmp += String.valueOf(board[row][col0]);
        }
        if (tmp.length() == 1)
            return true;
        else
            return check_words(tmp);
    }

    public boolean wo_board_check(boolean vertical, String word, int row, int col, char[][] board) {
        if (vertical) {
            if (!concate_string_v(board, true, 0, word, row, col))
                return false;
            for (int i = 0; i < word.length(); i++) {
                if (!concate_string_h(board, false, i, word, row + i, col))
                    return false;
            }
        } else {
            if (!concate_string_h(board, true, 0, word, row, col))
                return false;
            for (int i = 0; i < word.length(); i++) {
                if (!concate_string_v(board, false, i, word, row, col + i))
                    return false;
            }
        }
        return true;
    }

    public void input_word(boolean vertical, char[][] board, String word, int row, int col) {
        if (vertical) {
            for (int i = 0; i < word.length(); i++) {
                board[row + i][col] = word.charAt(i);
            }
        } else {
            for (int i = 0; i < word.length(); i++) {
                board[row][col + i] = word.charAt(i);
            }
        }
    }

    public boolean legal_input(boolean vertical, String word, int a, int b) {
        if (word.length() < 2)
            return false;
        if (vertical) {
            if (word.length() + a > this.getRows() - 1)
                return false;
            else
                return true;
        } else {
            if (word.length() + b > this.getColumns() - 1)
                return false;
            else
                return true;
        }
    }

    public String playWord(Play p, boolean dryRunOnly) {
        if (!legal_input(p.isVertical(), p.getWord(), p.getRow(), p.getCol()))
            return null;
        return_word = word_overlap(p.isVertical(), p.getWord().toLowerCase(), p.getRow(), p.getCol());
        if (return_word.equals("0"))
            return null;
        if (!wo_board_check(p.isVertical(), p.getWord().toLowerCase(), p.getRow(), p.getCol(), this.board_dat))
            return null;
        else {
            if (!dryRunOnly)
                input_word(p.isVertical(), this.board_dat, p.getWord().toLowerCase(), p.getRow(), p.getCol());
            return return_word;
        }
    }
}
