package app;

public class Problem_1_3_8 {
    public static void main(String[] args) {
        int num = Integer.parseInt(args[0]);
        for (int i = 1; i < num; i++) {
            if (i % 10 == 1 && i % 100 != 11) {
                System.out.println(i + "st Hello,World");
            } else if (i % 10 == 2 && i % 100 != 12) {
                System.out.println(i + "nd Hello,World");
            } else if (i % 10 == 3 && i % 100 != 13) {
                System.out.println(i + "rd Hello,World");
            } else {
                System.out.println(i + "th Hello,World");
            }

        }
    }
}