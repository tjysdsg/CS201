package app;

import java.util.Scanner;

public class Problem_1_4_18 {
    public static void printMatrix(double[][] m) {
        int n_rows = m.length;
        int n_cols = m[0].length;
        for (int i = 0; i < n_rows; ++i) {
            for (int j = 0; j < n_cols; ++j) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static double vectorDot(double[] a, double[] b) {
        assert (a.length == b.length);
        double result = 0;
        for (int i = 0; i < a.length; i++) {
            result += a[i] * b[i];
        }
        return result;
    }

    public static double[] getCol(double[][] m, int col) {
        int n_rows = m.length;
        double[] result = new double[n_rows];
        for (int r = 0; r < n_rows; ++r) {
            result[r] = m[r][col];
        }
        return result;
    }

    public static double[] getRow(double[][] m, int row) {
        return m[row];
    }

    public static void main(String[] args) {
        double[][] m1, m2;
        int n_rows1, n_cols1, n_rows2, n_cols2;
        Scanner scanner = new Scanner(System.in);

        // first matrix
        n_rows1 = scanner.nextInt();
        n_cols1 = scanner.nextInt();
        m1 = new double[n_rows1][n_cols1];
        for (int i = 0; i < n_rows1; ++i) {
            for (int j = 0; j < n_cols1; ++j) {
                m1[i][j] = scanner.nextDouble();
            }
        }
        // second matrix
        n_rows2 = scanner.nextInt();
        n_cols2 = scanner.nextInt();
        m2 = new double[n_rows2][n_cols2];
        for (int i = 0; i < n_rows2; ++i) {
            for (int j = 0; j < n_cols2; ++j) {
                m2[i][j] = scanner.nextDouble();
            }
        }

        scanner.close();

        // multiplication
        double[][] prod = new double[n_rows1][n_cols2];
        for (int i = 0; i < n_rows1; ++i) {
            for (int j = 0; j < n_cols2; ++j) {
                double[] row = getRow(m1, i);
                double[] col = getCol(m2, j);
                prod[i][j] = vectorDot(row, col);
            }
        }
        printMatrix(prod);
    }
}
