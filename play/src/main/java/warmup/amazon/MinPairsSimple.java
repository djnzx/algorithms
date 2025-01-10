package warmup.amazon;

import java.util.List;

public class MinPairsSimple {
  public static void main(String[] args) {
    List<Integer> data =
        Source.random_int_from_range(0, 100, 30);

    int min_idx = -1;
    int min_sum = Integer.MAX_VALUE;

    for (int idx=0; idx < data.size()-1; idx++) {
      int sum = data.get(idx) + data.get(idx+1);
      if (sum < min_sum) {
        min_idx = idx;
        min_sum = sum;
      }
    }
    System.out.printf("Array: %s\n", data);
    System.out.printf("Left index: %d\n", min_idx);
    System.out.printf("Right index: %d\n", min_idx+1);
    System.out.printf("The sum: %d\n", min_sum);
  }
}
