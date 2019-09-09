package app;

public class Problem_3_1_32 {
    private static boolean passwordVerification(String input) {
        boolean b_len = input.length() > 7;
        boolean b_digit = false;
        boolean b_upper = false;
        boolean b_lower = false;
        boolean b_symbol = false;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (!b_digit) {
                b_digit = Character.isDigit(c);
            }
            if (!b_upper) {
                b_upper = Character.isUpperCase(c);
            }
            if (!b_lower) {
                b_lower = Character.isLowerCase(c);
            }
            if (!b_symbol) {
                b_symbol = !Character.isAlphabetic(c) && !Character.isDigit(c);
            }
        }
        return b_len && b_digit && b_upper && b_lower && b_symbol;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Missing command line argument for input string");
            return;
        }
        System.out.println(passwordVerification(args[0]));
    }
}
