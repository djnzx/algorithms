package algorithms.recursion;

public class Factorial1 {

    public int do_calc_recursive(int number) {
        if (number == 1) return 1;
        return number * do_calc_recursive(number - 1);
    }

    public int do_calc_iterative(int number) {
        int result = 1;
        for (int el = 2; el <= number; el++) {
            result = result * el;
        }
        return result;
    }

    public static void main(String[] args) {
        Factorial1 f = new Factorial1();


        System.out.println(f.do_calc_recursive(5));

//        f.do_calc_recursive(5);
        int result = f.do_calc_recursive(5);
        System.out.println(result);
    }
}
