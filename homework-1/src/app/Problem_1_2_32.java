package app;

public class Problem_1_2_32 {
    private static double max(double a, double b, double c) {
        double res = Math.max(a, b);
        res = Math.max(res, c);
        return res;
    }

    public static void main(String[] args) {
        int r = Integer.parseInt(args[0]);
        int g = Integer.parseInt(args[1]);
        int b = Integer.parseInt(args[2]);

        double c = 0, m = 0, y = 0, k = 1;
        do {
            if (r == 0 && g == 0 && b == 0) {
                break;
            }
            double r_0 = r / 255.0;
            double g_0 = g / 255.0;
            double b_0 = b / 255.0;
            double w = max(r_0, g_0, b_0);
            c = (w - r_0) / w;
            m = (w - g_0) / w;
            y = (w - b_0) / w;
            k = 1 - w;
        } while (false);

        System.out.print("C: " + c + " ");
        System.out.print("M: " + m + " ");
        System.out.print("Y: " + y + " ");
        System.out.print("K: " + k + " ");
        System.out.print("\n");
    }
}
