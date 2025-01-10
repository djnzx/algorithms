package algorithms.l02bitwise.shift;

public class ShiftApp {

  public static void main(String[] args) {
    System.out.println(16 >> 2);  // 4
    System.out.println(16 >>> 2); // 4

    System.out.println(-16 >> 2); // -4
    System.out.println(-16 >>> 2);// 1073741820
    System.out.println("---");
    System.out.println(Integer.toBinaryString(-16));      // 11111111111111111111111111110000
    System.out.println(Integer.toBinaryString(-16 >> 2)); // 11111111111111111111111111111100
    System.out.println(Integer.toBinaryString(-16 >>> 2));// 00111111111111111111111111111100
  }

}
