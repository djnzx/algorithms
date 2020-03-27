package algorithms.l05binarysearch;

import algorithms.l01sort.Utils;

import java.util.Arrays;

public class BinarySearch1App {
  public static void main(String[] args) {
    int[] data = Utils.create_random_data(30); // 1..100
    int val_random = (int) (Math.random() * 100);
    int val_existed = data[(int) (Math.random() * 30)];
    BinarySearchV1 bs = new BinarySearchV1();

    Arrays.sort(data);
    System.out.println(Arrays.toString(data));

    int idx1 = bs.find(data, val_random);
    System.out.println(idx1 == -1 ?
        String.format("Value %d is not found", val_random) :
        String.format("Value %d is found at index %d", val_random, idx1)
    );
    System.out.printf("number of iteration %d\n", bs.counter);
    System.out.println();

    int idx2 = bs.find(data, val_existed);
    System.out.println(idx2 == -1 ?
        String.format("Value %d not found", val_existed) :
        String.format("Value %d is found at index %d", val_existed, idx2)
    );
    System.out.printf("number of iteration %d\n", bs.counter);
  }
}
