package app;

public class Problem_1_2_16 {
    public static void main(String[] args) {
        double G = 9.8;
        double mass1 = 1.0;
        double mass2 = 1.0;
        double r = 2.0;

        // previous statement
        double force = G * mass1 * mass2 / r * r;
        System.out.println("Wrong answer: " + force);
        // corrected one
        /* 
         * Explaination:
         *      The problem is the missing parenthesis after the division. Operator * and / has the same precedence and they have left to right assosiation. Hence,
         *      G * mass1 * mass2 / r is calculated first, and the result is multiplied by r.
         *      
         *      By adding a pair of parenthesis around r * r, the problem is fixed, also Math.pow is preferred.
        */
        double force1 = G * mass1 * mass2 / (r * r);
        // double force1 = G * mass1 * mass2 / Math.pow(r, 2);
        System.out.println("Corrected answer: " + force1);
        
    }
}