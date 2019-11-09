package core.generics_DIRTY;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AListUsage {
    public static void main(String[] args) {
        AList<Integer> aa = new AList<>(10);
        aa.put(1, 5);
        aa.put(2, 6);

        int i1 = aa.get(1);

        Function<String, Integer> f_to_int = (s) -> Integer.parseInt(s);
        int x5 = f_to_int.apply("5");

        Stream.of(1,2,3).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer x) {
                return x * 2;
            }
        });

        Predicate<Integer> test4 = new Predicate<Integer>() {
            @Override
            public boolean test(Integer x) {
                return x > 4;
            }
        };
        Predicate<Integer> test41 = x -> x > 4;
        Predicate<Integer> test42 = (Integer x) -> x > 4;


        Stream.of(1,2,3).map(x -> x * 2).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer x) {
                return x > 4;
            }
        });
        Stream.of(1,2,3).map(x -> x * 2).filter(x -> x > 4);
        Stream.of(1,2,3).map(x -> x * 2).filter(test4);

    }
}
