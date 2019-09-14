package algorithms.recursion;

public class XpowY {

    int do_calc(int number, int to) {
        if (to == 1) return number;
        return number * do_calc(number, to - 1);
    }

    public static void main(String[] args) {
        XpowY xy = new XpowY();
        int result = xy.do_calc(2, 3);

        System.out.println(result);
    }
}
