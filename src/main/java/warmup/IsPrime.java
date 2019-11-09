package warmup;

public class IsPrime {
  boolean check(int origin) {
    if (origin == 2) return true;
    for (int i = 2; i < Math.sqrt(origin); i++) {
      if (origin % i == 0) return false;
    }
    return true;
  }
}
