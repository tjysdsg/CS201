package app;

import java.util.*;

public class Group04WordFinder implements WordFinder {

    public void setupIndex(String wordListFilename, int maxWordLength) {
        System.out.println("Initializing WordFinder, it may take a while...");
        In filein = new In(wordListFilename);
        this.dictionary = filein.readAllLines();
        word_map = new HashMap<String, List<Integer>>();

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
        Collections.sort(lettersInHand);

        for (int r = 0; r < n_rows; ++r) {
            for (int c = 0; c < n_cols; ++c) {
                char ch = board.getLetterAt(r, c);
                if (ch != '0') {
                    List<Character> letters;
                    // check vertical words
                    for (int i = 0; i <= r; ++i) {
                        // make copy
                        letters = new LinkedList<Character>();
                        for (int s = 0; s < lettersInHand.size(); ++s) {
                            letters.add(lettersInHand.get(s).toCharArray()[0]);
                        }
                        List<Integer> string_indices = this.word_map.get(buildKey(letters, ch, i));
                        if (string_indices == null)
                            continue;
                        for (int si : string_indices) {
                            Play p = new Play(r - i, c, this.dictionary[si], true);
                            String word_played = board.playWord(p, true);
                            boolean legal = false;
                            if (word_played != null && word_played.length() > 0) {
                                legal = true;
                            } else
                                continue;
                            for (int j = 0; j < word_played.length(); ++j) {
                                int idx = letters.indexOf(word_played.charAt(j));
                                if (idx == -1) {
                                    legal = false;
                                    break;
                                }
                                letters.remove(idx);
                            }
                            if (legal)
                                result.add(new Candidate(p, word_played));
                        }
                    }
                    // check horizontal words
                    for (int i = 0; i <= c; ++i) {
                        // make copy
                        letters = new LinkedList<Character>();
                        for (int s = 0; s < lettersInHand.size(); ++s) {
                            letters.add(lettersInHand.get(s).toCharArray()[0]);
                        }
                        List<Integer> string_indices = this.word_map.get(buildKey(letters, ch, i));
                        if (string_indices == null)
                            continue;
                        for (int si : string_indices) {
                            Play p = new Play(r, c - i, this.dictionary[si], true);
                            String word_played = board.playWord(p, true);
                            boolean legal = false;
                            if (word_played != null && word_played.length() > 0) {
                                legal = true;
                            } else
                                continue;
                            for (int j = 0; j < word_played.length(); ++j) {
                                int idx = letters.indexOf(word_played.charAt(j));
                                if (idx == -1) {
                                    legal = false;
                                    break;
                                }
                            }
                            if (legal)
                                result.add(new Candidate(p, word_played));
                        }
                    }
                } else {
                    int max_len = 0;
                    // check neighbor
                    List<int[]> neighbors = Arrays
                            .asList(new int[][] { { r - 1, c }, { r + 1, c }, { r, c - 1 }, { r, c + 1 } });
                    for (int[] rc : neighbors) {
                        int r1 = rc[0];
                        int c1 = rc[1];
                        if (r1 >= 0 && r1 < n_rows && c1 >= 0 && c1 < n_cols && board.getLetterAt(r1, c1) != '0') {
                            for (Map.Entry<String, List<Integer>> e : this.word_map.entrySet()) {
                                for (int idx : e.getValue()) {
                                    String w = this.dictionary[idx];
                                    if (w.length() <= max_len) {
                                        continue;
                                    } else {
                                        Play p1 = new Play(r1, c1, w, true);
                                        Play p2 = new Play(r1, c1, w, false);
                                        String w1 = board.playWord(p1, true);
                                        String w2 = board.playWord(p2, true);
                                        boolean legal = false;
                                        // make copy
                                        List<Character> letters = new LinkedList<Character>();
                                        for (int s = 0; s < lettersInHand.size(); ++s) {
                                            letters.add(lettersInHand.get(s).toCharArray()[0]);
                                        }
                                        if (w1 != null && w1.length() > 0) {
                                            legal = true;
                                        } else
                                            continue;
                                        for (int j = 0; j < w1.length(); ++j) {
                                            int ix = letters.indexOf(w1.charAt(j));
                                            if (ix == -1) {
                                                legal = false;
                                                break;
                                            }
                                            letters.remove(ix);
                                        }
                                        if (legal)
                                            result.add(new Candidate(p1, w1));
                                        // make copy
                                        letters = new LinkedList<Character>();
                                        for (int s = 0; s < lettersInHand.size(); ++s) {
                                            letters.add(lettersInHand.get(s).toCharArray()[0]);
                                        }
                                        if (w2 != null && w2.length() > 0) {
                                            legal = true;
                                        } else
                                            continue;
                                        for (int j = 0; j < w2.length(); ++j) {
                                            int ix = letters.indexOf(w2.charAt(j));
                                            if (ix == -1) {
                                                legal = false;
                                                break;
                                            }
                                            letters.remove(ix);
                                        }
                                        if (legal)
                                            result.add(new Candidate(p2, w2));
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        return result;
    }

    // get characters that are alphabetically sorted
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

    private String buildKey(String char_list, char ch, int ch_i) {
        int i = char_list.indexOf(ch);
        String other_letters;
        if (i != -1)
            other_letters = char_list.substring(0, i) + char_list.substring(i);
        else
            other_letters = char_list;
        String key = ch + "_" + ch_i + "_" + other_letters;
        return key;
    }

    private String buildKey(List<Character> char_list, char ch, int ch_i) {
        String other_letters = "";
        int i = char_list.indexOf(ch);
        if (i != -1) {
            char_list.remove(i);
        }
        for (char c : char_list) {
            other_letters += c;
        }
        String key = ch + "_" + ch_i + "_" + other_letters;
        return key;
    }

    private void appendToWordMap(int word_index, String char_list, char ch, int ch_i) {
        String key = this.buildKey(char_list, ch, ch_i);
        // if current key exists, append
        if (this.word_map.containsKey(key)) {
            this.word_map.get(key).add(word_index);
        } else { // otherwise create a list
            LinkedList<Integer> string_list = new LinkedList<Integer>();
            string_list.add(word_index);
            this.word_map.put(key, string_list);
        }
    }

    private Map<String, List<Integer>> word_map;
    private String[] dictionary;
}