package algorithms.l11unionfind.disjoint;

import java.util.*;

public class CountOfCities {
  public static void main(String[] args) {
    String s = "6 5\n" +
        "0 1\n" +
        "0 2\n" +
        "1 2\n" +
        "2 3\n" +
        "4 5";
    Scanner in = new Scanner(
        s
//        System.in
    );

    int V = in.nextInt();
    int E = in.nextInt();
    DisjointSet ds = new DisjointSet(V);
    for (int i = 0; i < E; i++) {
      ds.add(in.nextInt(), in.nextInt());
    }
    List<Integer> sets = ds.sets();
    sets.forEach(val -> System.out.println(val+" "));
  }
}
