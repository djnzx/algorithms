package algorithms.l02bitwise.conversion2;

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
