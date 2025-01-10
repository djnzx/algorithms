package algorithms.l05binarysearch;

import algorithms.l01sort.Utils;

import java.util.Arrays;

public class BinarySearch2App {
  public static void main(String[] args) {
    int[] data = Utils.create_random_data(30); // 1..100
    int val_random = (int) (Math.random() * 100);
    int val_existed = data[(int) (Math.random() * 30)];

    Arrays.sort(data);
    System.out.println(Arrays.toString(data));

    BinarySearchV2.Result result1 = BinarySearchV2.find(data, val_random);
    BinarySearchV2.Result result2 = BinarySearchV2.find(data, val_existed);

    System.out.println(!result1.found ?
        String.format("Value %d not found", val_random) :
        String.format("Value %d is found at index %d", val_random, result1.index)
    );
    System.out.printf("number of iteration %d\n", result1.counter);
    System.out.println();

    System.out.println(!result2.found ?
        String.format("Value %d not found", val_existed) :
        String.format("Value %d is found at index %d", val_existed, result2.index)
    );
    System.out.printf("number of iteration %d\n", result2.counter);
  }
}
