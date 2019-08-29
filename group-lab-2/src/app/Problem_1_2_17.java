package app;

public class Problem_1_2_17 {
    public static void main(String[] args) {
        {
            int a = 1; // 1
            System.out.println(a);
            a = a + a; // 2
            System.out.println(a);
            a = a + a; // 4
            System.out.println(a);
            a = a + a; // 8
            System.out.println(a);
        }
        {
            boolean a = true; // true
            System.out.println(a);
            a = !a; // false
            System.out.println(a);
            a = !a; // true
            System.out.println(a);
            a = !a; // false
            System.out.println(a);
        }
        {
            int a = 2; // 2
            System.out.println(a);
            a = a * a; // 4
            System.out.println(a);
            a = a * a; // 16
            System.out.println(a);
            a = a * a; // 256
            System.out.println(a);
        }
    }
}