package algorithms.bitwise;

import java.util.Arrays;
import java.util.List;

public class NegativeRepresentation {
  /**
   * In computer science we have several notations:
   * One Complement representation
   * https://www.ntu.edu.sg/home/ehchua/programming/java/images/DataRep_OneComplement.png
   * and
   * Two Complement representation
   * https://www.ntu.edu.sg/home/ehchua/programming/java/images/DataRep_TwoComplement.png
   * Java uses 2's Complement representation
   *
   * https://www.cs.cornell.edu/~tomf/notes/cps104/twoscomp.html#twotwo
   * https://stackoverflow.com/questions/2811319/difference-between-and
   */
  public static void main(String[] args) {
    //   0 - 00000000
    //   1 - 00000001
    //   2 - 00000010
    //  ..
    // 127 - 01111111
    //-128 - 10000000
    //-127 - 10000001
    //  -2 - 11111110
    //  -1 - 11111111

    List<Integer> data = Arrays.asList(127, 126, 2, 1, 0, -1, -2, -127, -128);
    data.forEach(n -> System.out.printf("Decimal:%4d, Binary:%s\n", n, Integer.toBinaryString(n)));
  }
}
