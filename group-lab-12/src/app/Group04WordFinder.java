package app;

import java.util.*;

public class Group04WordFinder implements WordFinder {

    public void setupIndex(String wordListFilename, int maxWordLength) {
        System.out.println("Initializing WordFinder, it may take a while...");
        In filein = new In(wordListFilename);
        List<String> dictionaryList = new LinkedList<String>(Arrays.asList(filein.readAllLines()));
        // TODO: optimized using List of indices of string as the value of word_map
        word_map = new HashMap<String, Map<String, List<String>>>();

        int dict_len = dictionaryList.size();
        for (int i = 0; i < dict_len; ++i) {
            String word = dictionaryList.get(i);
            int word_len = word.length();
            if (word_len <= maxWordLength) {
                // get unique characters that are alphabetically sorted
                String char_list = getAlpha(word.toCharArray());
                // make a key value pair for every position of every word
                for (int j = 0; j < word_len; ++j) {
                    appendToWordMap(word, char_list, word.charAt(j), j);
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
        Map<String, List<String>> possible_entries = new HashMap<String, List<String>>();
        for (Map.Entry<String, Map<String, List<String>>> e : word_map.entrySet()) {
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
                for (Map.Entry<String, List<String>> e1 : e.getValue().entrySet()) {
                    possible_entries.put(e1.getKey(), e1.getValue());
                }
            }
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

                List<String> words = new LinkedList<String>();
                for (Map.Entry<String, List<String>> e : possible_entries.entrySet()) {
                    words.addAll(e.getValue());
                }
                Set<String> current_candidates = new HashSet<>(words);
                Set<String> prev_candidates = new HashSet<String>(current_candidates);
                for (int offset = 1; c + offset >= n_cols; ++offset) {
                    char curr_char = board.getLetterAt(r, c + offset);
                    String key = "" + curr_char + offset;
                    List<String> curr_words = possible_entries.get(key);
                    if (curr_words == null) {
                        break;
                    }
                    current_candidates = new HashSet<>(curr_words);
                    intersect(current_candidates, prev_candidates);
                    if (current_candidates.isEmpty()) {
                        current_candidates = prev_candidates;
                        break;
                    }
                    prev_candidates = new HashSet<String>(current_candidates);
                }
                // add new candidate
                for (Iterator<String> iter = current_candidates.iterator(); iter.hasNext();) {
                    String w = iter.next();
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
                prev_candidates = new HashSet<String>(current_candidates);
                for (int offset = 1; r + offset >= n_rows; ++offset) {
                    char curr_char = board.getLetterAt(r + offset, c);
                    String key = "" + curr_char + offset;
                    List<String> curr_words = possible_entries.get(key);
                    if (curr_words == null) {
                        break;
                    }
                    current_candidates = new HashSet<>(curr_words);
                    intersect(current_candidates, prev_candidates);
                    if (current_candidates.isEmpty()) {
                        current_candidates = prev_candidates;
                        break;
                    }
                    prev_candidates = new HashSet<String>(current_candidates);
                }
                // add new candidate
                for (Iterator<String> iter = current_candidates.iterator(); iter.hasNext();) {
                    String w = iter.next();
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

    private void appendToWordMap(String word, String char_list, char ch, int ch_i) {
        String outer_key = char_list;
        String inner_key = "" + ch + ch_i;
        Map<String, List<String>> outer_map = word_map.get(outer_key);
        if (outer_map == null) {
            outer_map = new HashMap<String, List<String>>();
            LinkedList<String> string_list = new LinkedList<String>();
            string_list.add(word);
            outer_map.put(inner_key, string_list);
            word_map.put(outer_key, outer_map);
        } else {
            // if current key exists, append
            if (outer_map.containsKey(inner_key)) {
                outer_map.get(inner_key).add(word);
            } else { // otherwise create a list
                LinkedList<String> string_list = new LinkedList<String>();
                string_list.add(word);
                outer_map.put(inner_key, string_list);
            }
        }
    }

    // NOTE: a is modified
    private void intersect(Set<String> a, Set<String> b) {
        a.retainAll(b);
    }

    private Map<String, Map<String, List<String>>> word_map;
}