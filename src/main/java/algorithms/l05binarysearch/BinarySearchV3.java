package algorithms.l05binarysearch;

import java.util.Optional;

public class BinarySearchV3 {
  public final static class Result {
    public final Optional<Integer> index;
    public final int counter;

    private Result(Optional<Integer> index, int counter) {
      this.index = index;
      this.counter = counter;
    }

    public static Result found(int index, int counter) {
      return new Result(Optional.of(index), counter);
    }

    public static Result notFound(int counter) {
      return new Result(Optional.empty(), counter);
    }
  }

  public static Result find(final int[] data, final int value) {
    return find(data, value, 0, data.length - 1, 1);
  }

  public static Result find(final int[] data, final int value, final int left, final int right, final int counter) {
    if (left > right || value < data[left] || value > data[right])
      return Result.notFound(counter);

    final int middle = (left + right) / 2;
    if (value == data[middle]) return Result.found(middle, counter);

    final int left2 = value > data[middle] ? middle + 1 : left;
    final int right2 = value < data[middle] ? middle - 1 : right;

    return find(data, value, left2, right2, counter+1);
  }
}
