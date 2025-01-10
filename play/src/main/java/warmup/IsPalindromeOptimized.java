package warmup;

public class IsPalindromeOptimized {
  boolean check(int number) {
    int[] digits = new int[10];
    int count = 0;
    while (number > 0) {
      digits[count++] = number % 10;
      number /= 10;
    }
    for (int i = 0; i < count / 2; i++) {
      if (digits[count-i-1] != digits[i]) return false;
    }
    return true;
  }
}
