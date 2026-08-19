package djnz.leetcode.aop.L01;

// на лекції 2
// https://leetcode.com/problems/find-numbers-with-even-number-of-digits/
class T1295 {
    public int findNumbers(int[] nums) {
        int count = 0;

        for (int n : nums) {
            if (String.valueOf(n).length() % 2 == 0) {
                count++;
            }
        }

        return count;
    }
}
