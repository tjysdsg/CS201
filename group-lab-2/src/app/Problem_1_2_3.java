package app;

public class Problem_1_2_3{
    public static void main(String[] args){
        boolean a = true;
        boolean a1 = false;
        boolean b = false;
        boolean b1 = true;
        boolean result1 = (!(a && b) && (a || b)) || ((a && b) || !(a || b)); 
        boolean result2 = (!(a1 && b) && (a1 || b)) || ((a1 && b) || !(a1 || b));
        boolean result3 = (!(a && b1) && (a || b1)) || ((a && b1) || !(a || b1));
        boolean result4 = (!(a1 && b1) && (a1 || b1)) || ((a1 && b1) || !(a1 || b1));

        System.out.println("a = true, b = false: " + result1);
        System.out.println("a = false, b = false: " + result2);
        System.out.println("a = true, b = true: " + result3);
        System.out.println("a = false, b = true: " + result4);
    }
}