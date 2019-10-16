package app;

import java.util.*;

public class Group04WordFinder implements WordFinder {

    public void setupIndex(String wordListFilename, int maxWordLength) {
        System.out.println("Initializing WordFinder, it may take a while...");
        In filein = new In(wordListFilename);
        this.dictionary = filein.readAllLines();
        word_map = new HashMap<String, Map<String, List<Integer>>>();

        int dict_len = this.dictionary.length;
        for (int i = 0; i < dict_len; ++i) {
            String word = this.dictionary[i];
            int word_len = word.length();
            if (word_len <= maxWordLength) {
                // get unique characters that are alphabetically sorted
                String char_list = getAlpha(word.toCharArray());
                // make a key value pair for every position of every word
                for (int j = 0; j < word_len; ++j) {
                    appendToWordMap(i, char_list, word.charAt(j), j);
                }
            }
        }
        System.out.println("Initialized WordFinder");
    }

    public SortedSet<Candidate> findCandidates(Board board, List<String> lettersInHand) {
        SortedSet<Candidate> result = new TreeSet<Candidate>();
        int n_rows = board.getRows();
        int n_cols = board.getColumns();
        // sorted letters
        Collections.sort(lettersInHand);
        Set<String> letter_set = new HashSet<String>(lettersInHand);

        // find possible keys that at most contains all letters in lettersInHand
        // BUG: this won't cover candidates that have letters that are not in hand, but
        // still can be legally placed. Because it uses the letters already on the board
        // to form a word, but this algorithm is good enough at finding candidates
        // without losing major performance, so ignored
        Map<String, List<Integer>> possible_entries = new HashMap<String, List<Integer>>();
        for (Map.Entry<String, Map<String, List<Integer>>> e : word_map.entrySet()) {
            Set<String> letter_set_copy = new HashSet<String>(letter_set);
            boolean possible = true;
            String k = e.getKey();
            for (int idx = 0; idx < k.length(); ++idx) {
                if (!letter_set_copy.contains("" + k.charAt(idx))) {
                    possible = false;
                    break;
                }
                letter_set_copy.remove("" + k.charAt(idx));
            }
            if (possible) {
                for (Map.Entry<String, List<Integer>> e1 : e.getValue().entrySet()) {
                    possible_entries.put(e1.getKey(), e1.getValue());
                }
            }
        }
        List<Integer> words = new LinkedList<Integer>();
        for (Map.Entry<String, List<Integer>> e : possible_entries.entrySet()) {
            words.addAll(e.getValue());
        }
        // main loop, brute force, check every starting coordinate for a word (except
        // the last row when vertical and the last column when horizontal)
        for (int r = 0; r < n_rows; ++r) {
            for (int c = 0; c < n_cols; ++c) {
                // check horizontally
                // there is no point checking horizontally if the start column is one of the
                // last two columns
                if (c >= n_cols - 2) {
                    continue;
                }

                Set<Integer> current_candidates = new HashSet<>(words);
                Set<Integer> prev_candidates = new HashSet<>(current_candidates);
                for (int offset = 1; c + offset >= n_cols; ++offset) {
                    char curr_char = board.getLetterAt(r, c + offset);
                    String key = "" + curr_char + offset;
                    List<Integer> curr_words = possible_entries.get(key);
                    if (curr_words == null) {
                        break;
                    }
                    current_candidates = new HashSet<>(curr_words);
                    intersect(current_candidates, prev_candidates);
                    if (current_candidates.isEmpty()) {
                        current_candidates = prev_candidates;
                        break;
                    }
                    prev_candidates = new HashSet<>(current_candidates);
                }
                // add new candidate
                for (Iterator<Integer> iter = current_candidates.iterator(); iter.hasNext();) {
                    Integer wi = iter.next();
                    String w = this.dictionary[wi];
                    if (w.length() <= 1) {
                        continue;
                    }
                    Play play = new Play(r, c, w, false);
                    String placed_word = board.playWord(play, true);
                    // it is still possible to be an illegal word, since we haven't check the
                    // letters after `offset`
                    if (placed_word == null) {
                        continue;
                    }
                    result.add(new Candidate(play, placed_word));
                    // if the length of placed_word is the the width of a row, it is definitely the
                    // best horizontal option
                    if (placed_word.length() == n_cols) {
                        break;
                    }
                }
                // check vertically
                // there is no point checking vertically if the start row is one of the
                // last two rows
                if (r >= n_rows - 2) {
                    continue;
                }
                current_candidates = new HashSet<>(words);
                prev_candidates = new HashSet<>(current_candidates);
                for (int offset = 1; r + offset >= n_rows; ++offset) {
                    char curr_char = board.getLetterAt(r + offset, c);
                    String key = "" + curr_char + offset;
                    List<Integer> curr_words = possible_entries.get(key);
                    if (curr_words == null) {
                        break;
                    }
                    current_candidates = new HashSet<>(curr_words);
                    intersect(current_candidates, prev_candidates);
                    if (current_candidates.isEmpty()) {
                        current_candidates = prev_candidates;
                        break;
                    }
                    prev_candidates = new HashSet<>(current_candidates);
                }
                // add new candidate
                for (Iterator<Integer> iter = current_candidates.iterator(); iter.hasNext();) {
                    int wi = iter.next();
                    String w = this.dictionary[wi];
                    if (w.length() <= 1) {
                        continue;
                    }
                    Play play = new Play(r, c, w, true);
                    String placed_word = board.playWord(play, true);
                    // it is still possible to be an illegal word, since we haven't check the
                    // letters after `offset`
                    if (placed_word == null) {
                        continue;
                    }
                    result.add(new Candidate(play, placed_word));
                    // if the length of placed_word is the the height of a column, it is definitely
                    // the best vertical option
                    if (placed_word.length() == n_rows) {
                        break;
                    }
                }
            }
        }
        return result;
    }

    // get unique characters that are alphabetically sorted
    private String getAlpha(char[] char_seq) {
        List<Character> char_list = new LinkedList<Character>();
        for (int l = 0; l < char_seq.length; ++l) {
            char_list.add(char_seq[l]);
        }
        String char_string = "";
        Collections.sort(char_list);
        for (Iterator<Character> iter = char_list.iterator(); iter.hasNext();) {
            char ch = iter.next();
            char_string += "" + ch;
        }
        return char_string;
    }

    private void appendToWordMap(int word_index, String char_list, char ch, int ch_i) {
        String outer_key = char_list;
        String inner_key = "" + ch + ch_i;
        Map<String, List<Integer>> outer_map = word_map.get(outer_key);
        if (outer_map == null) {
            outer_map = new HashMap<String, List<Integer>>();
            LinkedList<Integer> l = new LinkedList<Integer>();
            l.add(word_index);
            outer_map.put(inner_key, l);
            word_map.put(outer_key, outer_map);
        } else {
            // if current key exists, append
            if (outer_map.containsKey(inner_key)) {
                outer_map.get(inner_key).add(word_index);
            } else { // otherwise create a list
                LinkedList<Integer> string_list = new LinkedList<Integer>();
                string_list.add(word_index);
                outer_map.put(inner_key, string_list);
            }
        }
    }

    // NOTE: a is modified
    private void intersect(Set<Integer> a, Set<Integer> b) {
        a.retainAll(b);
    }

    private Map<String, Map<String, List<Integer>>> word_map;
    private String[] dictionary;
}