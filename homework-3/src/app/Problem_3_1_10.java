package app;

import app.Problem_3_3_20;

public class Problem_3_1_10 {
    public static class GeneExt extends Problem_3_3_20.Gene {
        public GeneExt(String dna) {
            super(dna);
        }

        public String complementWatsonCrick() {
            char[] result = new char[dna.length()];
            for (int i = 0; i < this.dna.length(); ++i) {
                result[i] = this.dna.charAt(i);
                switch (result[i]) {
                case 'C':
                    result[i] = 'G';
                    break;
                case 'G':
                    result[i] = 'C';
                    break;
                case 'A':
                    result[i] = 'T';
                    break;
                case 'T':
                    result[i] = 'A';
                    break;
                default:
                    break;
                }
            }
            return new String(result);
        }
    }

    public static int[] testComplementWatsonCrick() {
        int[] result = new int[] { 0, 2 };
        GeneExt good = new GeneExt("ATG" + "CGC" + "CTG" + "CGT" + "CTG" + "TAC" + "TAG");
        if (("TAC" + "GCG" + "GAC" + "GCA" + "GAC" + "ATG" + "ATC").equals(good.complementWatsonCrick())) {
            ++result[0];
        }
        GeneExt bad = new GeneExt("AT" + "GCG" + "CTG" + "CGT" + "CTG" + "TAC" + "TAG");
        if (("TA" + "CGC" + "GAC" + "GCA" + "GAC" + "ATG" + "ATC").equals(bad.complementWatsonCrick())) {
            ++result[0];
        }
        return result;
    }

    public static void main(String[] args) {
        int[] test1 = testComplementWatsonCrick();
        System.out.println("Test for complementWatsonCrick(): " + test1[0] + " out of " + test1[1] + " passed.");
    }
}
