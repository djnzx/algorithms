package djnz.leetcode.aop.L01;

// на лекції 1
// https://leetcode.com/problems/convert-the-temperature/
class T2469 {
    public double[] convertTemperature(double celsius) {
        return new double[]{
                celsius + 273.15,
                celsius * 1.80 + 32.00
        };
    }
}