package algorithms.l13lee;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LeeDemoApp {

  public static void main(String[] args) {
    Lee lee = new Lee(15, 10);

    List<LPoint> obstacles = Stream.of(
      IntStream.rangeClosed(0, 7).mapToObj(y -> new LPoint(5, y)),
      IntStream.rangeClosed(2, 9).mapToObj(y -> new LPoint(10, y))
    ).flatMap(a -> a).collect(Collectors.toList());

    LPoint start = LPoint.of(0, 0);
    LPoint finish = LPoint.of(14, 9);

    Optional<Iterable<LPoint>> path = lee.trace(start, finish, obstacles, true);
    System.out.println(path);
  }

}
