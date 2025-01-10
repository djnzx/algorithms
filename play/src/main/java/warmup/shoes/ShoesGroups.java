package warmup.shoes;

import java.util.Stack;

public class ShoesGroups {
  public int calc(String origin) {
    int groups = 0;
    Stack<Character> tail = new Stack<>();
    tail.push(origin.charAt(0));

    for (int i = 1; i < origin.length(); i++) {
      char c = origin.charAt(i);
      if (!(c == 'L' || c == 'R')) throw new IllegalArgumentException(String.format("Wrong char encountered: '%c'", c));
      if (tail.isEmpty()) {
        tail.push(c);
        continue;
      }
      switch (c) {
        case 'L': switch (tail.peek()) {
                    case 'L': tail.push('L'); break;
                    case 'R': tail.pop(); break;
                  }
                  break;
        case 'R': switch (tail.peek()) {
                    case 'L': tail.pop(); break;
                    case 'R': tail.push('R'); break;
                  }
                  break;
      }
      if (tail.isEmpty()) groups++;
    }
    if (!tail.isEmpty()) throw new IllegalArgumentException("pairs aren't corresponding");
    return groups;
  }
}
