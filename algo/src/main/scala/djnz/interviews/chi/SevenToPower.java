package djnz.interviews.chi;

import java.util.stream.IntStream;

public class SevenToPower {

  public static void main(String[] args) {
    int r = IntStream
      .rangeClosed(1, 1000001)
      .reduce(1, (a, b) -> a * 7 % 10);

    System.out.println(r);
  }

}
