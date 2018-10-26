import java.util.Scanner;

public class AppCosinus {
    public static long factorial_recursive(long n) {
        return n==1 ? 1 : n * factorial(n-1);
    }

    public static long factorial(long n) {
        long r = 1;
        for (long i = 2; i <= n; i++) {
             r *= i;
        }
        return r;
    }

    public static void main(String[] args) {
        final Scanner in = new Scanner(System.in);

        System.out.print("Enter the X:");
        final double x = in.nextDouble();

        System.out.print("Enter the epsilon:");
        final double epsilon = in.nextDouble();

        double expected = Math.cos(x);
        System.out.printf("Expected:    %.16f\n", expected);

        double calculated = 1;
        double elem;

        for (long n=1; ; n++) {
            double e1 = Math.pow(x, n*2);
            long e2 = factorial(n*2);
            elem = Math.pow(-1, n) * e1 / e2;
            calculated += elem;
            System.out.printf("iteration:%2d, e1:%27.16f, e2:%16d, elem:%19.16f, result:%19.16f\n", n, e1, e2, elem, calculated);
            if (Math.abs(elem) < epsilon) break;
        }

        System.out.println("cos(x)=");
        System.out.printf("Expected:    %19.16f\n", expected);
        System.out.printf("Calculated:  %19.16f\n", calculated);
        System.out.printf("Comp mistake:%19.16f\n", Math.abs(calculated - expected));
        System.out.printf("Epsilon:     %19.16f\n", epsilon);
    }
}
