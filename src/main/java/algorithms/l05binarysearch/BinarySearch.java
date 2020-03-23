package algorithms.l05binarysearch;

public class BinarySearch {
  public int counter = 0;

  public boolean find(int[] data, int val) {
    int left = 0;
    int right = data.length - 1;
    while (left < right) {
      counter++;
      int middle = (left + right) / 2;
      if (data[middle] == val) return true;
      if (data[middle] < val) left = middle + 1;
      else if (data[middle] > val) right = middle - 1;
    }
    return false;
  }
}
