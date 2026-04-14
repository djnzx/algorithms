package algorithms.l05binarysearch;

public class BinarySearchV1 {

  public int counter = 0;

  /**
   * find the given element in the sorted array
   * by using binary search algorithms
   * -1 if not found
   */
  public int find(int[] data, int value) {
    counter = 0;
    int left = 0;
    int right = data.length - 1;
    if (value < data[left]) return -1;
    if (value > data[right]) return -1;
    while (left <= right) {
      counter++;
      int middle = (left + right) / 2;
      if      (value > data[middle]) left  = middle + 1;
      else if (value < data[middle]) right = middle - 1;
      else return middle;
    }
    return -1;
  }
}
