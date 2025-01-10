package algorithms.l08graph;

import algorithms.l08graph.rep.Graph;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Tools {

  public static LinkedList<Integer> toLL(Collection<Integer> data) {
    return data instanceof LinkedList ? (LinkedList<Integer>)data : new LinkedList<>(data);
  }

  public static void print_visited_array(boolean[] visited) {
    // print indices
    System.out.print("Indices:");
    IntStream.range(0, visited.length).forEach(i ->
      System.out.printf("%3d", i)
    );
    System.out.println();

    // print values
    System.out.print("Values: ");
    IntStream.range(0, visited.length).forEach(i ->
      System.out.printf("%3s", visited[i] ? "*" : "")
    );
    System.out.println();
  }

  public static Graph copyGraph(Graph orig, Supplier<Graph> constructorRef) {
    Graph copy = constructorRef.get();
    IntStream.range(0, orig.v())
      .forEach(src ->
        orig.children(src).forEach(dst ->
          copy.addEdge(src, dst)));

    return copy;
  }

}
