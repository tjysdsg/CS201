package app;

public class Problem_3_3_20 {
    public static class Gene {
        public Gene() {
            this.dna = "";
        };

        public Gene(String dna) {
            this.dna = dna;
        }

        public void addNucleotide(char c) {
            this.dna += "" + c;
        }

        public char nucleotideAt(int i) {
            return this.dna.charAt(i);
        }

        public boolean isPotentialGene() {
            // Length is a multiple of 3.
            if (dna.length() % 3 != 0)
                return false;
            // Starts with start codon.
            if (!dna.startsWith("ATG"))
                return false;
            // No intervening stop codons.
            for (int i = 3; i < dna.length() - 3; i++) {
                if (i % 3 == 0) {
                    String codon = dna.substring(i, i + 3);
                    if (codon.equals("TAA"))
                        return false;
                    if (codon.equals("TAG"))
                        return false;
                    if (codon.equals("TGA"))
                        return false;
                }
            }

            // Ends with a stop codon.
            if (dna.endsWith("TAA"))
                return true;
            if (dna.endsWith("TAG"))
                return true;
            if (dna.endsWith("TGA"))
                return true;

            return false;
        }

        protected String dna;
    }

    public static int[] testIsPotentialGene() {
        int[] result = new int[] { 0, 2 };
        Gene good = new Gene("ATGCGCCTGCGTCTGTACTAG");
        if (true == good.isPotentialGene()) {
            ++result[0];
        }
        Gene bad = new Gene("ATGCGCTGCGTCTGTACTAG");
        if (false == bad.isPotentialGene()) {
            ++result[0];
        }
        return result;
    }

    public static void main(String[] args) {
        int[] test1 = testIsPotentialGene();
        System.out.println("Test for isPotentialGene(): " + test1[0] + " out of " + test1[1] + " passed.");
    }
}
