package djnz.leetcode.aop.L01;

// практика 2
// https://leetcode.com/problems/smallest-even-multiple/
class T2413 {
    public int smallestEvenMultiple(int n) {
        if (n % 2 == 0) {
            return n;
        }

        return n * 2;
    }
}
