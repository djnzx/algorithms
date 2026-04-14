package algorithms.l05binarysearch;

import algorithms.l01sort.Utils;
import java.util.Arrays;

public class BinarySearch3App {
  public static void main(String[] args) {
    int[] data = Utils.create_random_data(30); // 1..100
    int val_random = (int) (Math.random() * 100);
    int val_existed = data[(int) (Math.random() * 30)];

    Arrays.sort(data);
    System.out.println(Arrays.toString(data));

    BinarySearchV3.Result result1 = BinarySearchV3.find(data, val_random);
    BinarySearchV3.Result result2 = BinarySearchV3.find(data, val_existed);

    String message1 = result1.index
        .map(idx -> String.format("Value %d is found at index %d", val_random, idx))
        .orElse(String.format("Value %d not found", val_random));
    System.out.println(message1);
    System.out.printf("number of iteration %d\n", result1.counter);
    System.out.println();

    String message2 = result2.index
        .map(idx -> String.format("Value %d is found at index %d", val_existed, idx))
        .orElse(String.format("Value %d not found", val_existed));
    System.out.println(message2);
    System.out.printf("number of iteration %d\n", result2.counter);
  }
}
