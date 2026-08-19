package djnz.leetcode.aop.L01;

// практика 1
// https://leetcode.com/problems/number-of-good-pairs/
class T1512 {
    public int numIdenticalPairs(int[] nums) {
        int pairs = 0;

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    pairs++;
                }
            }
        }

        return pairs;
    }
}
