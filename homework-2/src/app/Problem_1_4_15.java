package app;

import java.util.Scanner;

public class Problem_1_4_15 {
    public static void main(String[] args) {
        // input
        Scanner scanner = new Scanner(System.in);
        int n_rows = scanner.nextInt();
        int n_cols = scanner.nextInt();
        int[][] m = new int[n_rows][n_cols];
        for (int i = 0; i < n_rows; ++i) {
            for (int j = 0; j < n_cols; ++j) {
                m[j][i] = scanner.nextInt();
            }
        }
        scanner.close();
        // print
        for (int i = 0; i < n_rows; ++i) {
            for (int j = 0; j < n_cols; ++j) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }
}
