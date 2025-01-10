package core.iterable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class HiddenData implements Iterable<String> {

  private final List<String> data = Arrays.asList(
      "Winter", "Summer", "Spring", "Autumn"
  );

  @Override
  public Iterator<String> iterator() {
    return data.iterator();
  }

  private final int[] data2 = new int[] {1,2,3,4};

  public Iterator<Integer> iterator_data2() {
//    Iterator<String> iterator1 = data.iterator();
//    Iterator<Integer> iterator2 = data2.iterator();

    Iterator<Integer> myIterator = new Iterator<Integer>() {
      int current = 0;

      @Override
      public boolean hasNext() {
        return current < data2.length;
      }

      @Override
      public Integer next() {
//        Integer current_element =
        return data2[current++];
//        current++;
//        return String.format("<%d>", current_element);
      }
    };

    return myIterator;
  }

}
