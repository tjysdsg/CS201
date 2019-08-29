package app;

public class Problem_1_2_28 {
    public static void main(String[] args) {
        int[] nums = { Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]) };
        int sign = Integer.signum(nums[1] - nums[0]);
        int new_sign = Integer.signum(nums[2] - nums[1]);
        if (sign != new_sign) {
            System.out.println("false");
        } else {
            System.out.println("true");
        }
    }
}
