package app;

public class Problem_1_2_18 {
    public static void main(String[] args) {
        double num1 = Double.parseDouble(args[0]);
        double num2 = Double.parseDouble(args[1]);
        double result = Math.sqrt(Math.pow(num1, 2) + Math.pow(num2, 2));
        System.out.println(result);
    }
}