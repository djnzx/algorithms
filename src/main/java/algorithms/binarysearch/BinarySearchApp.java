package algorithms.binarysearch;

import algorithms.sort.Utils;

import java.util.Arrays;

public class BinarySearchApp {
  public static void main(String[] args) {
    int[] data = Utils.create_random_data(30);
    int val = (int) (Math.random() * 100);
    Arrays.sort(data);
    System.out.println(Arrays.toString(data));
    System.out.printf("val:%d\n", val);

    BinarySearch app = new BinarySearch();
    System.out.println(app.find(data, val));

    System.out.printf("number of iteration:%d\n", app.counter);
  }
}
