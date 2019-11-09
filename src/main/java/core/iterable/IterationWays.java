package core.iterable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class IterationWays {

  public ArrayList<String> data() {
    return new ArrayList<String>() {{
      add("Java");
      add("Scala");
      add("C++");
      add("Haskell");
    }};
  }

  public List<String> data3() {
    return Arrays.asList("Java", "Scala", "C++", "Haskell");
  }

  public static void main(String[] args) {
    IterationWays app = new IterationWays();
    ArrayList<String> data = app.data();

    System.out.println("Approach 1. naive iteration via get() by index manually");
    for (int i = 0; i < data.size(); i++) {
      System.out.printf("** %s **\n", data.get(i));
    }

    System.out.println("Approach 2. foreach syntax. It uses Iterable<T> and Iterator<T> under the hood");
    for (String val: data) {
      System.out.printf("<< %s >>\n", val);
    }

    System.out.println("Approach 3. direct iterator usage with while-do");
    Iterator<String> it = data.iterator();
    while (it.hasNext()) {
      System.out.printf(">> %s <<\n", it.next());
    }

    System.out.println("Approach 4. collections embedded forEach iterator");
    data.forEach(s -> System.out.printf(")) %s ((\n", s));

    System.out.println("Approach 5. streams embedded forEach terminate operator");
    data.stream().forEach(s -> System.out.printf("(( %s ))\n", s));
  }
}
