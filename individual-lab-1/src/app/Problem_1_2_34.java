package app;

public class Problem_1_2_34 {
    private static int min(int[] nums) {
        int res = nums[0];
        for (int i = 0; i < nums.length; ++i) {
            res = res > nums[i] ? nums[i] : res;
        }
        return res;
    }

    private static int max(int[] nums) {
        int res = nums[0];
        for (int i = 0; i < nums.length; ++i) {
            res = res < nums[i] ? nums[i] : res;
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = new int[3];
        for (int i = 0; i < 3; ++i) {
            nums[i] = Integer.parseInt(args[i]);
        }
        int min_ = min(nums);
        int max_ = max(nums);
        int mid = nums[0] + nums[1] + nums[2] - min_ - max_;
        System.out.println(min_ + " " + mid + " " + max_);
    }
}