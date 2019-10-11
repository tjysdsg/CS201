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
            for (int j = 0; j < word_len; ++j) {
                // calculate key
                SortedSet<Character> char_set = new TreeSet<Character>();
                for (int l = 0; l < word.length(); ++l) {
                    char_set.add(word.charAt(l));
                }
                String char_string = "";
                for (Iterator<Character> iter = char_set.iterator(); iter.hasNext();) {
                    char ch = iter.next();
                    char_string += "" + ch;
                }
                String key = "" + word.charAt(j) + j + "_" + char_string;
                if (word_map.containsKey(key)) {
                    word_map.get(key).add(word);
                } else {
                    LinkedList<String> string_list = new LinkedList<String>();
                    string_list.add(word);
                    word_map.put(key, string_list);
                }
            }
        }
        System.out.println("Initialized WordFinder");
    }

    public SortedSet<Candidate> findCandidates(Board board, List<String> lettersInHand) {
        SortedSet<Candidate> result = new TreeSet<Candidate>();
        return result;
    }

    private LinkedHashMap<String, LinkedList<String>> word_map;
}