package app;

public class Problem_1_3_29 {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (i % 2 == 0 ^ j % 2 == 0) {
                    System.out.print(" ");
                } else {
                    System.out.print("*");
                }
            }
            System.out.print("\n");
        }
    }
}
