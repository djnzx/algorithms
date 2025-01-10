package algorithms.l05binarysearch;

public class BinarySearchV2 {

  public static class Result {
    public final boolean found;
    public final int index;
    public final int counter;

    private Result(boolean found, int index, int counter) {
      this.found = found;
      this.index = index;
      this.counter = counter;
    }

    public static Result found(int index, int counter) {
      return new Result(true, index, counter);
    }

    public static Result notFound(int counter) {
      return new Result(false, -1, counter);
    }
  }

  public static Result find(int[] data, int value) {
    int counter = 0;
    int left = 0;
    int right = data.length - 1;
    if (value < data[left]) return Result.notFound(counter);
    if (value > data[right]) return Result.notFound(counter);
    while (left <= right) {
      counter++;
      int middle = (left + right) / 2;
      if      (value > data[middle]) left  = middle + 1;
      else if (value < data[middle]) right = middle - 1;
      else return Result.found(middle, counter);
    }
    return Result.notFound(counter);
  }
}
