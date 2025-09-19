package warmup.amazon;

import java.util.ArrayList;
import java.util.List;

public class Houses {
  public List<Integer> cellCompete(int[] states, int days) {
    int len = states.length;
    boolean[] full = new boolean[states.length + 2];
    for (int i=0; i<len; i++) {
      full[i+1] = states[i] == 1;
    }
    while (days > 0) {
      boolean[] next = new boolean[full.length];
      for(int i=1; i<=states.length; i++) {
        next[i] = full[i-1] ^ full[i+1];
      }
      days--;
      full = next;
    }
    ArrayList<Integer> result = new ArrayList<>();
    for (int i=0; i<states.length; i++) {
      result.add(full[i+1] ? 1 : 0);
    }
    return result;
  }
}
