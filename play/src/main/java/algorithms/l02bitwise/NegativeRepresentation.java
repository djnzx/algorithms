package algorithms.l02bitwise;

import algorithms.l02bitwise.conversion.IntToBin;

import java.util.Arrays;
import java.util.List;

public class NegativeRepresentation {

  public static void main(String[] args) {
    //   0 - 00000000
    //   1 - 00000001
    //   2 - 00000010
    //   3 - 00000011
    //  ..
    // 126 - 01111110
    // 127 - 01111111
    //-128 - 10000000
    //-127 - 10000001
    //-126 - 10000010
    //  ..
    //  -3 - 11111101
    //  -2 - 11111110
    //  -1 - 11111111
    IntToBin ib = new IntToBin();

    Arrays.asList(0,1,2,3, 126,127,-128,-127,-126,-3,-2,-1)
      .forEach(n ->
        System.out.printf("Decimal:%4d, Binary:%s\n", n, ib.toBin(n))
      );
  }
}
