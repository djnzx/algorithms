package algorithms.l02bitwise.usecases;

public class RealExampleShop {

  final static int MASK_INSTOCK = 0b000001;
  final static int MASK_VIP =     0b000010;
  final static int MASK_OPTION3 = 0b000100;

  public static void main(String[] args) {
    int flag1 = MASK_OPTION3 | MASK_INSTOCK;
    System.out.println(flag1); // 101 -> 5

    int flag2 = MASK_OPTION3 | MASK_VIP;
    System.out.println(flag2); // 110 -> 6

    boolean checked_vip1 = (flag2 & MASK_VIP) > 0; // true
    boolean checked_vip2 = (flag1 & MASK_VIP) > 0; // false

    System.out.println(checked_vip1);
    System.out.println(checked_vip2);

  }
}
