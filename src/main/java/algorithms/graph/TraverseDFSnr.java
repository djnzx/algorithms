package algorithms.graph;

import java.util.LinkedList;
import java.util.List;

public class TraverseDFSnr {

  private final XGraph graph;

  public TraverseDFSnr(XGraph graph) {
    this.graph = graph;
  }

  public List<Integer> traverse(int source) {
    // real path
    final List<Integer> path_ = new LinkedList<>();
    // track the process
    LinkedList<Integer> process = new LinkedList<>();
    // start vertex
    process.add(source);
    // main loop
    while (!process.isEmpty()) {
      int current = process.poll(); // head
      path_.add(current);
      graph.getEdgesFrom(current).descendingIterator()
          // add to head in reverse order
          .forEachRemaining(process::addFirst);
    }
    return path_;
  }

}
