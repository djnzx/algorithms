package algorithms.l02bitwise.dec_to_bin;

/**
 * http://www.asciitable.com/index/asciifull.gif
 * https://cdn.cs50.net/2016/x/psets/0/pset0/bulbs.html
 * http://sticksandstones.kstrom.com/appen.html
 */
public class DecToBinV3 {
  public static void main(String[] args) {
//    int value = new Scanner(System.in).nextInt(); // 17
    int value =  18;
    StringBuilder binary = new StringBuilder();
    for (int i = 7; i >= 0 ; i--) {
      int part = value >> i;
      int bit = part & 0b00000001;
      binary.append(bit);
    }
    System.out.println(binary.toString()); // 00010010

//    00010010 >> 0 => 00010010
//    00010010 >> 1 => 00001001
//    00010010 >> 2 => 00000100
//    00010010 >> 3 => 00000010
//    00010010 >> 4 => 00000001
//
//    0b00001001
//  & 0b00000001
//    ----------
//    0b00000001
  }
}
