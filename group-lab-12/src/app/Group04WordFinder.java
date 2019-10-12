package app;

import java.util.*;

public class Group04WordFinder implements WordFinder {

    public void setupIndex(String wordListFilename, int maxWordLength) {
        System.out.println("Initializing WordFinder, it may take a while...");
        In filein = new In(wordListFilename);
        List<String> dictionaryList = new LinkedList<String>(Arrays.asList(filein.readAllLines()));
        word_map = new LinkedHashMap<String, LinkedList<String>>();

        int dict_len = dictionaryList.size();
        for (int i = 0; i < dict_len; ++i) {
            String word = dictionaryList.get(i);
            int word_len = word.length();
            if (word_len > maxWordLength) {
                dictionaryList.remove(i);
                --dict_len;
                --i;
                continue;
            }

            // get unique characters that are alphabetically sorted
            String char_string = getAlphaUnique(word);

            // make a key value pair for every position of every word
            for (int j = 0; j < word_len; ++j) {
                // calculate key
                String key = buildKey(word, j, char_string);

                // if current key exists, append
                if (word_map.containsKey(key)) {
                    word_map.get(key).add(word);
                } else { // otherwise create a list
                    LinkedList<String> string_list = new LinkedList<String>();
                    string_list.add(word);
                    word_map.put(key, string_list);
                }
            }
            // finally, key "0" will contain all words
            if (word_map.containsKey("0")) {
                word_map.get("0").add(word);
            } else { // otherwise create a list
                LinkedList<String> string_list = new LinkedList<String>();
                string_list.add(word);
                word_map.put("0", string_list);
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
        String letters = getAlphaUnique(String.join("", lettersInHand).toCharArray());
        //
        for (int r = 0; r < n_rows; ++r) {
            for (int c = 0; c < n_cols; ++c) {
                // check horizontally
                List<String> words = word_map.get("0");
                Set<String> current_candidates = new HashSet<>(words);
                Set<String> prev_candidates = new HashSet<String>(current_candidates);
                for (int offset = 1; offset < n_cols; ++offset) {
                    char curr_char = board.getLetterAt(r, c + offset);
                    if (curr_char == '\u0000') {
                        break;
                    }
                    String key = buildKey(curr_char, offset, letters);
                    List<String> curr_words = word_map.get(key);
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
                    Play play = new Play(r, c, w, false);
                    String placed_word = board.playWord(play, true);
                    // it is still possible to be an illegal word, since we haven't check the
                    // letters after `offset`
                    if (placed_word == null) {
                        continue;
                    }
                    result.add(new Candidate(play, placed_word));
                }
                // check vertically
                words = word_map.get("0");
                current_candidates = new HashSet<>(words);
                prev_candidates = new HashSet<String>(current_candidates);
                for (int offset = 1; offset < n_rows; ++offset) {
                    char curr_char = board.getLetterAt(r + offset, c);
                    if (curr_char == '\u0000') {
                        break;
                    }
                    String key = buildKey(curr_char, offset, letters);
                    List<String> curr_words = word_map.get(key);
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
                    Play play = new Play(r, c, w, true);
                    String placed_word = board.playWord(play, true);
                    // it is still possible to be an illegal word, since we haven't check the
                    // letters after `offset`
                    if (placed_word == null) {
                        continue;
                    }
                    result.add(new Candidate(play, placed_word));
                }
            }
        }
        return result;
    }

    // get unique characters that are alphabetically sorted
    private String getAlphaUnique(String word) {
        SortedSet<Character> char_set = new TreeSet<Character>();
        for (int l = 0; l < word.length(); ++l) {
            char_set.add(word.charAt(l));
        }
        String char_string = "";
        for (Iterator<Character> iter = char_set.iterator(); iter.hasNext();) {
            char ch = iter.next();
            char_string += "" + ch;
        }
        return char_string;
    }

    // get unique characters that are alphabetically sorted
    private String getAlphaUnique(char[] char_seq) {
        SortedSet<Character> char_set = new TreeSet<Character>();
        for (int l = 0; l < char_seq.length; ++l) {
            char_set.add(char_seq[l]);
        }
        String char_string = "";
        for (Iterator<Character> iter = char_set.iterator(); iter.hasNext();) {
            char ch = iter.next();
            char_string += "" + ch;
        }
        return char_string;
    }

    private String buildKey(String word, int index, String other) {
        if (word.charAt(index) == '0') {
            return "0";
        }
        return "" + word.charAt(index) + "_" + index + "_" + other;
    }

    private String buildKey(char ch, int index, String other) {
        if (ch == '0') {
            return "0";
        }
        return "" + ch + "_" + index + "_" + other;
    }

    // NOTE: a is modified
    private void intersect(Set<String> a, Set<String> b) {
        a.retainAll(b);
    }

    private LinkedHashMap<String, LinkedList<String>> word_map;
}