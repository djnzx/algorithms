package algorithms.recursion;

public class Fibonacci2 {

    private int do_calc(int number) {
        System.out.println(number);
        if (number < 3) return 1;
        int t1 = do_calc(number - 1);
        int t2 = do_calc(number - 2);
        return t1 + t2;
    }

    public static void main(String[] args) {
        Fibonacci2 fb = new Fibonacci2();

        // N-th
        System.out.println(fb.do_calc(5));
    }

}
