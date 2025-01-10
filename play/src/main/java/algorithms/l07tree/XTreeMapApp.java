package algorithms.l07tree;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class XTreeMapApp {

    public static void main(String[] args) {
        XTreeMap<Integer, Integer> xt = new XTreeMap<>();

        List<Integer> data = IntStream.rangeClosed(1, 3).boxed().collect(Collectors.toList());
//        Collections.shuffle(data);
        data.forEach(xt::putB);
        System.out.println(xt);
    }

}
