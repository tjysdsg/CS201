package app;

import java.util.Random;

public class Problem_1_2_30 {
    private static double mean(double[] nums) {
        double sum = 0;
        int len = 0;
        for (double n : nums) {
            sum += n;
            len += 1;
        }
        return sum / len;
    }

    private static double min(double[] nums) {
        double res = nums[0];
        for (double n : nums) {
            res = Math.min(res, n);
        }
        return res;
    }

    private static double max(double[] nums) {
        double res = nums[0];
        for (double n : nums) {
            res = Math.max(res, n);
        }
        return res;
    }

    public static void main(String[] args) {
        Random rand = new Random();
        double[] nums = new double[5];
        for (int i = 0; i < 5; ++i) {
            nums[i] = rand.nextDouble();
            System.out.print(nums[i] + " ");
        }
        System.out.print("\n");
        System.out.println("Mean: " + mean(nums));
        System.out.println("Min: " + min(nums));
        System.out.println("Max: " + max(nums));
    }
}
