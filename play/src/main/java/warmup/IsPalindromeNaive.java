package warmup;

public class IsPalindromeNaive {
  boolean check(int number) {
    String s = String.valueOf(number);
    return s.contentEquals(new StringBuilder(s).reverse());
  }
}
