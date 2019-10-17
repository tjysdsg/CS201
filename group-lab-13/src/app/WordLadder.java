package app;

import java.util.List;
import java.util.ArrayList;;

public class WordLadder {
    public WordLadder() {
    }

    public WordLadder(String[] dictionary, int word_len) {
        setup(dictionary, word_len);
    }

    public void setup(String[] dictionary, int word_len) {
        int dict_len = dictionary.length;
        List<String> dict = new ArrayList<String>();
        for (int i = 0; i < dict_len; ++i) {
            String s = dictionary[i];
            if (s.length() == word_len) {
                dict.add(s);
            }
        }
        dict_len = dict.size();
        this.graph = new Graph();
        for (int i = 0; i < dict_len; ++i) {
            for (int j = 0; j < dict_len; ++j) {
                if (i >= j)
                    continue;
                int d = distance(dict.get(i), dict.get(j));
                if (d == 1) {
                    this.graph.addEdge(dict.get(i), dict.get(j));
                }
            }
        }
    }

    public Iterable<String> findPath(String start, String end) {
        this.pf = new PathFinder(this.graph, start);
        if (pf.hasPathTo(end)) {
            return pf.pathTo(end);
        }
        return null;
    }

    private int distance(String a, String b) {
        int len = a.length();
        int cost = 0;
        for (int i = 0; i < len; ++i) {
            // costs of: insertion, match and deletion
            char ca = a.charAt(i);
            char cb = b.charAt(i);
            cost += ca == cb ? 0 : 1;
        }
        return cost;
    }

    private Graph graph;
    private PathFinder pf;
}