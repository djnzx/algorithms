package warmup.amazon;

import java.util.*;
import java.util.stream.IntStream;

public class MinPairs {
  private int total = 0;

  class ZPair {
    int idx;
    int sum;

    ZPair(int idx, int sum) {
      this.idx = idx;
      this.sum = sum;
    }
  }

  int sum_at(int idx, List<Integer> parts) {
    return parts.get(idx) + parts.get(idx+1);
  }

  int find_min_pair_index(List<Integer> parts) {
    return IntStream
        .range(0, parts.size() - 1)
        .mapToObj(idx -> new ZPair(idx, sum_at(idx, parts)))
        .min((p1, p2) -> p1.sum - p2.sum)
        .orElseThrow(() -> new IllegalArgumentException("list size expected to be > 0"))
        .idx;
  }

  int find_min_pair_index2(List<Integer> parts) {
    // initial data
    int idx1 = 0;
    int idx2 = 1;
    int v1 = parts.get(idx1);
    int v2 = parts.get(idx2);
    int min = v1 + v2;
    int current = idx2 + 1;
    // loop until end
    while (current < parts.size()) {
      int v30 = parts.get(current);
      int v31 = parts.get(current-1);
      if ((current-idx2 == 1) && (v30 + v2 <= min)) {
        idx1 = idx2;
        idx2 = current;
        min = v2 + v30;
      } else
      if ((current-idx2 > 1) && (v30 + v31 <= min)) {
        idx1 = current - 1;
        idx2 = current;
        min = v31 + v30;
      }
      current++;
    }
    return idx1;
  }

  List<Integer> reduce(List<Integer> origin) {
    List<Integer> reduced = new ArrayList<>(origin.size() - 1);
    int min_pair_index = find_min_pair_index(origin);
    Iterator<Integer> it = origin.iterator();
    while (min_pair_index > 0) {
      reduced.add(it.next());
      min_pair_index--;
    }
    int sum = it.next() + it.next();
    reduced.add(sum);
    it.forEachRemaining(reduced::add);
    total+=sum;
    return reduced;
  }

  // reduce by minimal pair
  List<Integer> reduce2(List<Integer> parts) {
    System.out.printf("List before reducing: %s\n", parts);
    int min_pos = find_min_pair_index(parts);
    System.out.printf("index: %d\n", min_pos);
    List<Integer> reduced = new ArrayList<>(parts.size() - 1);
    Iterator<Integer> it = parts.iterator();

    int index = 0;
    // prior elements
    while (index < min_pos) {
      int el = it.next();
      reduced.add(el);
      index++;
    }
    // new element
    int sum = it.next() + it.next();
    reduced.add(sum);
    // residual elements
    while (it.hasNext()) {
      int el = it.next();
      reduced.add(el);
    }
    System.out.printf("List after reducing:  %s\n", reduced);
    total+=sum;
    System.out.printf("sum  : %d\n", sum);
    System.out.printf("total: %d\n", total);
    return reduced;
  }

  int minimumTime(int numOfParts, List<Integer> parts) {
    if (numOfParts != parts.size()) {
      throw new IllegalArgumentException("wrong data");
    }
//        int iteration=1;
//        List<Integer> process = parts;
    while (parts.size() > 1) {
//            System.out.printf("= Iteration: %d =\n", iteration++);
      parts = reduce(parts);
    }
    return total;
  }

  public static void main(String[] args) {
    MinPairs s = new MinPairs();
    int total=
//                s.minimumTime(4, Arrays.asList(8,4,6,12)); // 58, OK
//                s.minimumTime(4, Arrays.asList(20, 4, 8, 2)); // me 58, test: 54
//                s.minimumTime(6, Arrays.asList(1,2,5,10,35,89)); // 224 OK
        s.minimumTime(7, Arrays.asList(6,4,2,1,1,3,5)); // 57, OK
    System.out.printf("Total time is: %d\n", total);
  }
}
