package warmup;

import java.util.Scanner;

public class Nth3and4 {

  public static String fmt(int m) {
    if (m<=0) throw new IllegalArgumentException("number is supposed to be more than zero");
    if (m==1) return "%d-st";
    if (m==2) return "%d-nd";
    if (m==3) return "%d-rd";
    return "%d-th";
  }

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    System.out.print("Enter the number:");
    int m = in.nextInt();
    System.out.printf(fmt(m), m);
    int mth = 0;
    while ((m > 0) && ((mth+=1) > 0)) {
      if (mth % 3 == 0 || mth % 4 == 0) { m-=1; }
    }
    System.out.printf(" number is:%d\n", mth);
  }
}
