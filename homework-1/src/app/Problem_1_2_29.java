package app;

public class Problem_1_2_29 {
    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int d = Integer.parseInt(args[1]);
        int y = Integer.parseInt(args[2]);

        int y_0 = y - (14 - m) / 12;
        int x = y_0 + y_0 / 4 - y_0 / 100 + y_0 / 400;
        int m_0 = m + 12 * ((14 - m) / 12) - 2;
        int d_0 = (d + x + (31 * m_0) / 12) % 7;
        System.out.println(d_0);
    }
}
