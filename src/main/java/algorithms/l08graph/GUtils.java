package algorithms.l08graph;

public class GUtils {
  public static void print_visited_array(boolean[] visited) {
    // print indices
    System.out.print("Indices:");
    for (int i = 0; i < visited.length; i++) {
      System.out.printf("%3d", i);
    }
    System.out.println();
    // print values
    System.out.print("Values: ");
    for (int i = 0; i < visited.length; i++) {
      System.out.printf("%3s", visited[i] ? "*": "");
    }
    System.out.println();
  }

}
